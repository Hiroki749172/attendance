package com.example.demo.login.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//AOPのクラスには@Aspect。Bean定義するために@Componentもつける。この２つはセット
@Aspect
@Component
public class LogAspect {
	//@Aroundはメソッド実行の前後に、AOPの処理（Advice）を実行
	@Around("execution(* *..*.*Controller.*(..))")
	public Object startLog(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("メソッド開始 ： " + jp.getSignature());
		
		try {
			//proceedで実行し、return result;で実行結果の戻り値を返すので前後で実行できる
			Object result = jp.proceed();
			
			System.out.println("メソッド終了 ： " + jp.getSignature());
			return result;
		} catch (Exception e) {
			System.out.println("メソッド異常終了 ： " + jp.getSignature());
			e.printStackTrace();
			throw e;
		}
	}
	//UserDaoクラスのログ出力（どのクラスメソッドが呼ばれたか）
	@Around("execution(* *..*.*UserDao*.*(..))")
	public Object daoLog(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("メソッド開始 ： " + jp.getSignature());
		
		try {
			Object result = jp.proceed();
			
			System.out.println("メソッド終了 ： " + jp.getSignature());
			return result;
		} catch (Exception e) {
			System.out.println("メソッド異常終了 ： " + jp.getSignature());
			e.printStackTrace();
			throw e;
		}
	}
}	
	
	
	




//	//AOPを実行するメソッド。@Beforeはメソッドが実行される前に、AOPの処理（Advice）を実行
//	//パラメータにexecutionでどのクラスやメソッドが実行されたときにこのメソッドを呼び出す
//	//LoginControllerクラスのgetLoginメソッドをPointcut（実行場所）にしている
//	@Before("execution(* com.example.demo.login.controller.LoginController.getLogin(..))")
//	public void startLog(JoinPoint jp) {
//		System.out.println("メソッド開始 ： " + jp.getSignature());
//	}
//	
//	//@Afterはメソッドが実行された後に、AOPの処理（Advice）を実行
//	@After("execution(* com.example.demo.login.controller.LoginController.getLogin(..))")
//	public void endLog(JoinPoint jp) {
//		System.out.println("メソッド終了 ： " + jp.getSignature());





	//コントローラークラスの全てのメソッドを対象
//	@Before("execution(**..*.* Controller.*(..))")
//	public void startLog(JoinPoint jp) {
//		System.out.println("メソッド開始 ： " + jp.getSignature());
//	}
//	//
//	@After("execution(**..*.* Controller.*(..))")
//	public void endLog(JoinPoint jp) {
//		System.out.println("メソッド終了 ： " + jp.getSignature());
//	}

	/* アスタリスク（*）は任意の文字列。パッケージでは１階層のパッケージ名、メソッドの引数では１つの引数になる
	 * ドット2文字(..)はパッケージの記述箇所では任意（0以上）のパッケージになる。メソッドの引数では、任意（0以上）の引数
	 * プラス(+)はクラス名の後に指定すると、指定クラスのサブクラス／実装クラスが含まれます
	 * */
