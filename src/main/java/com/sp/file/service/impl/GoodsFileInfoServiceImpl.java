package com.sp.file.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sp.file.mapper.GoodsFileInfoMapper;
import com.sp.file.service.GoodsFileInfoService;
import com.sp.file.type.Status;
import com.sp.file.vo.GoodsFileInfoVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Primary
@Service
//@AllArgsConstructor //생성자 의존성 주입 자동으로 해주는 어노케이션
@RequiredArgsConstructor
@Slf4j
public class GoodsFileInfoServiceImpl implements GoodsFileInfoService {

	private final GoodsFileInfoMapper goodsFileMapper; // 레퍼런스 데이터 타입이므로 메모리를 생성해야하고, 그러면 생성자 호출이 됨
														// 생성자 호출될 때 의존성 주입!

	// private String uploadPath="C:\\dev\\upload"; -> 경로 바뀌면 바뀐 개수만큼 수정해줘야 하기 때문에
	// 불편
	// 밑에 걸로 사용
	@Value("${upload.file-path}")
	private String uploadFilePath;

	@Override
	public int insertGoodsFileInfo(GoodsFileInfoVO goodsFile) {
		return goodsFileMapper.insertGoodsFileInfo(goodsFile);
	}

	@Override
	public List<GoodsFileInfoVO> selectGoodsFileInfos(int giNum) {
		return goodsFileMapper.selectGoodsFileInfos(giNum);
	}

	@Override
	public int insertGoodsFileInfos(int giNum, List<GoodsFileInfoVO> goodsFiles) {
		int result = 0;
		for (GoodsFileInfoVO goodsFile : goodsFiles) {
			MultipartFile file = goodsFile.getFile();
			String originName = goodsFile.getFile().getOriginalFilename(); // abcd.png
			String extName = originName.substring(originName.lastIndexOf(".")); // .png
			String fileName = UUID.randomUUID() + extName;
			goodsFile.setGfiOriginName(originName);
			goodsFile.setGfiPath("/file/" + fileName);

			try {
				file.transferTo(new File(uploadFilePath + fileName));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			goodsFile.setGiNum(giNum);
			result += goodsFileMapper.insertGoodsFileInfo(goodsFile);
		}
		return result;
	}
	@Override
	public int updateGoodsFileInfos(int giNum, List<GoodsFileInfoVO> goodsFiles) {

		int result = 0;
		for(GoodsFileInfoVO goodsFile : goodsFiles) {
			if(goodsFile.getStatus() == Status.DELETE) {
				String fileName = goodsFile.getGfiPath();
				int idx = fileName.lastIndexOf("/")+1;
				fileName = fileName.substring(idx);
				File f = new File(uploadFilePath + fileName);
				if(f.exists()) {
					f.delete();
				}
				result += goodsFileMapper.deleteGoodsFileInfo(goodsFile.getGfiNum());
				continue;
			}
			MultipartFile file = goodsFile.getFile();
			if(file!=null) {
				String originName = file.getOriginalFilename(); // abcd.png
				String extName = originName.substring(originName.lastIndexOf(".")); //.png
				String fileName = UUID.randomUUID() + extName;
				goodsFile.setGfiOriginName(originName);
				goodsFile.setGfiPath("/file/" + fileName);
				try {
					file.transferTo(new File(uploadFilePath + fileName));
				} catch (IllegalStateException e) {
					log.error("file upload error=>{}", e);
				} catch (IOException e) {
					log.error("file upload error=>{}", e);
				}
			}
			goodsFile.setGiNum(giNum);

			if(goodsFile.getStatus() == Status.UPDATE) {
				result += goodsFileMapper.updateGoodsFileInfo(goodsFile);
			}else {
				result += goodsFileMapper.insertGoodsFileInfo(goodsFile);
			}
		}
		return result;
	}


}
