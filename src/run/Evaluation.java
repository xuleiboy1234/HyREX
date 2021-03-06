/*
 * Data:2007.11.17
 * by : WangYi  @ NWSUAF
 * Email : wangyi2000@nwsufa.edu.cn
 * 
 * adviser: ZhangYang @UQ
 *   Email : mokuram@itee.uq.edu.au
 *           zhangyang@nwsuaf.edu.cn
 * 
 * Project : TRI-Training Classification    
 * Description:
 *     This class is used for run the TriTrainer.
 *     
 * @article{TKDE05-zhou,
 *  title={{Tri-Training: Exploiting Unlabeled Data Using Three
 *    Classifiers}},
 *	author={Zhihua Zhou, and Ming Li},
 *	journal={IEEE Transactions on Knowledge and Data Engineering},
 * volume={17},
 * number={11},
 * pages={1529--1541},
 * year={2005},
 * publisher={IEEE Educational Activities Department Piscataway, NJ, USA}
 * }
 */

package run;



import weka.classifiers.Classifier;
import weka.core.Instances;
import java.io.*;
import weka.core.Utils;
import weka.core.converters.ConverterUtils.DataSource;

public class Evaluation {

	/**
	 * @param args
	 */
	//useage information for semi.tritrain.TriTrainer;
	public static void use()
	{
		System.out.println();
		System.out.println("useage for semi.tritrain.TriTrainer");
		System.out.println("     java semi.tritrain.TriTrainer -c <classifierName> -s <percentage> -t <name of training file> -T <name of test file>");
		System.out.println();
		System.out.println("Trainer options:");
		System.out.println();
		System.out.println("-c <classifierName>");
		System.out.println("    the name of classifier which we use to construct TriTrainer");
		System.out.println("-s <percentage>");
		System.out.println("    Sets the percentage for the labeled/unlabeled set split from training file, e.g., 0.20.");
		System.out.println("-t <name of training file>");
		System.out.println("    Sets training file");
		System.out.println("-T <name of test file>");
		System.out.println("    Sets test file");
	}
	
	//对输入的参数进行验证，并运行TriTrainer;
	public static void runTrainer( String[] options)
	{

	    String spl = "";
	    String classname ="";
	    String training = "";
	    String test = "";
	    double sp = 0.0;
	    Classifier classifier1 = null;
	    
	    Instances train_ins ;
	    try
	    {
	    	classname = Utils.getOption('c', options);
	    	spl = Utils.getOption('s', options);
	    	training = Utils.getOption('t', options);
	    	test = Utils.getOption('T', options);
	    	
	    	//验证classifierName
	    	if(classname.length()==0)
	    	{
	    		throw new Exception("have no classifier name!");
	    	}
	    	else
	    	{
	    		try
	    		{
	    		classifier1 = (Classifier)Class.forName(classname).newInstance();
	    		}
	    		catch(Exception e)
	    		{
	    			throw new Exception(e.getMessage() + " no found");
	    		}
	    	}
	    	
	    	//验证percentage
	    	if(spl.length()!=0)
	    	{
	    		try
	    		{
	    			sp = Double.parseDouble(spl);
	    		}
	    		catch(Exception e)
	    		{
	    			throw new Exception("the percentage for the labeled/unlabeled set split is wrong");
	    		}
	    		if(sp<=0 || sp >=1)
	    		{
	    			throw new Exception("the percentage for the labeled/unlabeled set split should be in (0.0,1.0)!");
	    		}
	    	}
	    	else
	    	{
	    		throw new Exception("have no the percentage for the labeled/unlabeled set split");
	    	}
	    	
            //验证name of training file
	    	if(training.length()==0)
	    	{
	    		throw new Exception("no training file given!");
	    	}
	    	else
	    	{
	    		if(!new File(training).exists())
	    			throw new Exception("training file no found");
	    		//new DataSource(training);
	    		try
	    		{
	    		train_ins=Util.getInstances(training);
	    		}
	    	    catch(Exception e)
	    		{
	    	    	//System.out.println(e + "xxxx");
	    		    throw new Exception(e.getMessage());
	    		}
	    		
	    	}
	    	
            //验证name of test file
	    	if(test.length()==0)
	    	{
	    		throw new Exception("no test file given!");
	    	}
	    	else
	    	{
	    		if(!new File(training).exists())
	    			throw new Exception("test file no found");
	    		
	    		try
	    		{
	    		Util.getInstances(test);
	    		}
	    	    catch(Exception e)
	    		{
	    	    	//System.out.println(e + "xxxx");
	    		    throw new Exception(e.getMessage());
	    		}
	    	}
	    	Tritrainer tra = new Tritrainer(classname,training,test,sp);
			tra.buildClassifier(train_ins);
	    }
	    catch(Exception e)
	    {
	    	System.out.println("semi Exception:" + e.getMessage());
	    	Evaluation.use();
	    }
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	    
	   /* String spl = "";
	    String classname ="";
	    String training = "";
	    String test = "";
	    double sp = 0.0;
	    Classifier classifier1 = null;
	    try
	    {
	    	classname = Utils.getOption('c', args);
	    	spl = Utils.getOption('s', args);
	    	training = Utils.getOption('t', args);
	    	test = Utils.getOption('T', args);
	    	
	    	if(classname.length()==0)
	    	{
	    		throw new Exception("have no classifier name!");
	    	}
	    	else
	    	{
	    		try
	    		{
	    		classifier1 = (Classifier)Class.forName(classname).newInstance();
	    		}
	    		catch(Exception e)
	    		{
	    			throw new Exception(e.getMessage() + "no found");
	    		}
	    	}
	    	if(spl.length()!=0)
	    	{
	    		try
	    		{
	    			sp = Double.parseDouble(spl);
	    		}
	    		catch(Exception e)
	    		{
	    			throw new Exception("the percentage for the labeled/unlabeled set split is wrong");
	    		}
	    	}
	    	else
	    	{
	    		
	    	}
	    	if(training.length()==0)
	    	{
	    		throw new Exception("no training file given!");
	    	}
	    	else
	    	{
	    		if(!new File(training).exists())
	    			throw new Exception("training file no found");
	    	}
	    	if(test.length()==0)
	    	{
	    		throw new Exception("no test file given!");
	    	}
	    	else
	    	{
	    		if(!new File(training).exists())
	    			throw new Exception("test file no found");
	    	}
	    }
	    catch(Exception e)
	    {
	    	System.out.println("semi Exception:" + e.getMessage());
	    	Evalution.use();
	    }*/
		
	}

}
