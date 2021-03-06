package run;


import weka.core.Instances;
import weka.core.Instance;
import weka.core.converters.ArffLoader;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;

import java.io.*;
public class Util {

	/**
	 * 从样本集U中获取x个样本
	 */
	public static Instances getInstances(Instances U, int x)
	{
		Instances ins = new Instances(U,0);
	    int length = U.numInstances();
	    if(length >= x)
	    {
	    	for(int i = 0; i < x ; i ++)
	    	{
				Instance intan = U.instance(i);
	    		ins.add(intan);
	    	}
	    }
		else
		{
			for(int i = 0; i < length ; i ++)
			{
				Instance intan = U.instance(i);
				ins.add(intan);
				//Flag = false;
			}
		}
	        return ins;

	}
	
	/*
	 * 用分类器h分类ins样本集合，将置信度超过阈值confident的样本标记后，放入已标记集合。
	 */
	public static Instances doExample(Classifier h,Instances ins,double confident) 
	{
		int length = ins.numInstances();
		Instances j = new Instances(ins,length);
		for(int m = 0; m < length ; m++)
		{
			Instance tem = ins.instance(m);
			try
			{
				double[] dist = h.distributionForInstance(tem);
				double predValue = h.classifyInstance(tem);
				int pred = (int)predValue;
				Double data = dist[pred];
				
				//如果置信度高于confident时，标记该样本，将其放入标记集合中。
				if(data >= confident )
				{
					System.out.println("sdfj");
					System.out.println(tem.classValue());
					tem.setClassValue(predValue);
					System.out.println(tem.classValue());
					j.add (tem);
				}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}
		return j;
	}
	public void setLabledInstance(Classifier h,Instance x)
	{
		
	}
	
	/*
	 * 计算分类器的错误率；
	 */
	public static double errorRate(Classifier h,Instances x) 
	{
		try 
		{
			Instance testInst;
			Evaluation testingEvaluation = new Evaluation(x);
		    int length = x.numInstances(); 
			for (int i =0; i < length; i++) {
			testInst = x.instance(i);
			testingEvaluation.evaluateModelOnceAndRecordPrediction(
		            h, testInst);
		      }
			//Evaluation eva = new Evaluation(x);
			//eva.evaluateModel(h,x);
			//return eva.errorRate();
		      return testingEvaluation.errorRate();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return 1.0;
		
	}
	
	/*
	 * 合并两个样本集合；产生一个新的集合；
	 */
	public static Instances add(Instances ins1,Instances ins2)
	{
		int length1 = ins1.numInstances();
		int length2 = ins2.numInstances();
		Instances ins1New = new Instances(ins1);
		Instances ins2New = new Instances(ins2);
		if(length1 >= length2)
		{
			for(int i =0;i < length2; i ++)
			{
				ins1New.add(ins2New.instance(i));
			}
			return ins1New;
		}
		else
		{
			for(int i =0;i < length1; i ++)
			{
				ins2New.add(ins1New.instance(i));
			}
			return ins2New;
		}
	}
	/*
	 * 合并两个样本集合；未产生新集合；
	 */
	public static Instances addTo(Instances ins1,Instances ins2)
	{
		
		int length2 = ins2.numInstances();	
		for(int i =0;i < length2; i ++)
			{
				ins1.add(ins2.instance(i));
			}
			return ins1;
	}
	
	/*
	 * 从.arff文件中获取样本Instances;
	 * 
	 */
	public static Instances getInstances(String filename) throws Exception
	{
		File file= new File(filename);
		return getInstances(file);
	}
	
	/*
	 * 从.arff文件中获取样本Instances;
	 * 
	 */
	public static Instances getInstances(File file) throws Exception
	{
		Instances inst = null;
		try
		{
			ArffLoader loader = new ArffLoader();
			loader.setFile(file);
			inst = loader.getDataSet();
		}
		catch(Exception e)
		{
			throw new Exception(e.getMessage() );
			//System.out.println("wesdfsdf");
		}
		return inst;
	}
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Util.getInstances("F:\\eclipse_workspace\\1.arff");
		/*ArffLoader loader = new ArffLoader();
		ArffLoader loader2 = new ArffLoader();
		ArffLoader loader3 = new ArffLoader();
		File file = new File("e:\\data\\diabetes_training_labeled.arff");
		File file2 = new File("e:\\data\\diabetes_training_unlabeled.arff");
		File file3 = new File("e:\\data\\diabetes_training.arff");
		
		
		loader.setFile(file);
		loader2.setFile(file2);
		loader3.setFile(file3);
		Random ran = new Random();
		Instances inst = getInstances(file);//loader.getDataSet();
		Instances instx = getInstances(file2);//loader2.getDataSet();
		Instances inst3 = getInstances(file3);
		inst.setClassIndex(inst.numAttributes()-1);
		instx.setClassIndex(inst.numAttributes()-1);
		inst3.setClassIndex(inst.numAttributes()-1);
		int n =inst.numInstances();
		
		Instances inst2 = getInstances(instx,100);
		
		J48 classifier = new J48();
		
		
		classifier.buildClassifier(inst);
		System.out.println("inst = " + inst.numInstances());
		System.out.println("xxx" + errorRate(classifier,inst3));
		
		System.out.println("instxfsdfsdfsd =" +instx.numInstances());
		Util.addTo(instx, inst3);
		System.out.println("instxfsdfsdfsd =" +instx.numInstances());
		/*Instances instance1 = doExample(classifier,inst2,0.75);
		for (int i = 0; i < instance1.numInstances(); i ++ )
		{
			//System.out.println("wangyi");
			Instance x = instance1.instance(i);
			//System.out.println("sfs");
			//System.out.println(classifier.classifyInstance(x));
			//System.out.println(x.stringValue(35));
		}
		/*for (int i = 0; i < instance1.numInstances(); i ++ )
		{
			//System.out.println("wangyi");
			Instance x = instance1.instance(i);
			//System.out.println("sfs");
			//System.out.println(classifier.classifyInstance(x));
			System.out.println(inst.numInstances());
			inst.add(x);
			System.out.println(inst.numInstances());
			classifier.buildClassifier(inst);
			System.out.println("xxx" + errorRate(classifier,instx));//System.out.println(x.stringValue(35));
		}*/
		/*
		for (int i = 0 ; i < 80 ; i ++)
		{
			Instance x = instx.instance(i);
			inst.add(x);
		}
		for (int i = 0; i < inst.numInstances(); i ++ )
		{
			//System.out.println("wangyi");
			Instance x = inst.instance(i);
			//System.out.println("sfs");
			//System.out.println(classifier.classifyInstance(x));
			System.out.println(x.stringValue(inst.numAttributes()-1));
		}
		System.out.println("inst = " + inst.numInstances());
		//J48 classifier1 = new J48();
		classifier.buildClassifier(inst);
		System.out.println("xxx" + errorRate(classifier,inst3));*/
		
		

	}

}
