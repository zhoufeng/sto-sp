package com.shenma.top.imagecopy.util.strategy;

import java.util.HashMap;
import java.util.Map;

public class TopStrategyFactory {
	
	enum StrategyType {
		OneToOneStrategy,OneToTwoStrategy,OthersStrategy,ThreeToTwoStrategy,TwoToOneStrategy,TwoTwoStrategy,ZoreToOneStrategy;
	}
	private static Map<StrategyType,TopToAliIStrategy> strategyMap=new HashMap<StrategyType,TopToAliIStrategy>(8){

		private static final long serialVersionUID = 1L;
		{
			put(StrategyType.OneToOneStrategy,new OneToOneStrategy());
			put(StrategyType.OneToTwoStrategy,new OneToTwoStrategy());
			put(StrategyType.OthersStrategy,new OthersStrategy());
			put(StrategyType.ThreeToTwoStrategy,new ThreeToTwoStrategy());
			put(StrategyType.TwoToOneStrategy,new TwoToOneStrategy());
			put(StrategyType.TwoTwoStrategy,new TwoTwoStrategy());
			put(StrategyType.ZoreToOneStrategy,new ZoreToOneStrategy());
		}
		
		
	};
	
	public static TopToAliIStrategy getInstance(Integer taobaoSpcNum,Integer aliSpcNum){
		TopToAliIStrategy strategy=null;
		StrategyType type=null;
		if(taobaoSpcNum==aliSpcNum&&aliSpcNum==2){
			type=StrategyType.TwoTwoStrategy;
		}else if(taobaoSpcNum==aliSpcNum&&aliSpcNum==1){
			type=StrategyType.OneToOneStrategy;
		}else if(taobaoSpcNum==3&&aliSpcNum==2){
			type=StrategyType.ThreeToTwoStrategy;
		}else if(taobaoSpcNum==2&&aliSpcNum==1){
			type=StrategyType.TwoToOneStrategy;
		}else if(taobaoSpcNum==1&&aliSpcNum==2){
			type=StrategyType.OneToTwoStrategy;
		}else if(taobaoSpcNum==0&&aliSpcNum==1){
			type=StrategyType.ZoreToOneStrategy;
		}else{
			type=StrategyType.OthersStrategy;
		}
		return strategyMap.get(type);
	}
}
