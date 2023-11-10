package com.sp.file.common.aop;

import java.util.Collections;
import java.util.HashMap;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class TransactionAOP {

	@Autowired
	private TransactionManager transactionManager;
	
	@Bean
	TransactionInterceptor transactionAdvice() {
		TransactionInterceptor txInterceptor = new TransactionInterceptor();
		NameMatchTransactionAttributeSource txAttributeSource=new NameMatchTransactionAttributeSource();
		RuleBasedTransactionAttribute txAttribute = new RuleBasedTransactionAttribute();
		
		HashMap<String,TransactionAttribute> txMethods=new HashMap<>();
		txMethods.put("insert", txAttribute);
		txMethods.put("update", txAttribute);
		txMethods.put("delete", txAttribute);
		txMethods.put("save", txAttribute);
		txMethods.put("add", txAttribute);
		txMethods.put("modify", txAttribute);
		txAttributeSource.setNameMap(txMethods);
		
		txAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		
		txInterceptor.setTransactionAttributeSource(txAttributeSource);
		txInterceptor.setTransactionManager(transactionManager);
		
		return txInterceptor;
	}
	
	@Bean
	Advisor transactionAdviceAdvisor() {
		AspectJExpressionPointcut pointcut=new AspectJExpressionPointcut();
		pointcut.setExpression("excution(* com.sp.file..*ServiceImpl.*(..))");
		return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
	}
}
