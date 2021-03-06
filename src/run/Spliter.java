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
 *     This class is used for split a file into two parts with the given precentage.
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

import weka.core.Instances;
import java.io.*;

import weka.core.Utils;

public class Spliter {

	/**
	 * @param args
	 */
	//将集合inst写入文件名为filename的文件中；
	public void outputFile(Instances inst, String filename)
	{
		try
		{
		FileOutputStream fileoutputstream = new FileOutputStream(filename);// 建立文件输出流  
        // System.out.println(file2[14]);
         
		byte tag_bytes[] = inst.toString().getBytes(); 
          fileoutputstream.write(tag_bytes);
          fileoutputstream.close();
		}
		catch(Exception e)
		{
			
		}
	}
	
    //useage for semi.tritrain.spliter；
	public static void use()  
	{
		System.out.println();
		System.out.println("useage for semi.tritrain.Spliter");
		System.out.println("     java semi.tritrain.Spliter -s <percentage> -i <name of input file> -T <name of output test file> -t <name of output training file> ");
		System.out.println();
		System.out.println("Spliter options:");
		System.out.println();
		
		System.out.println("-s <percentage>");
		System.out.println("    Sets the percentage for the test/training set split from input file, e.g., 0.30.");
		
		System.out.println("-i <InputFileName>");
		System.out.println("    the name of InputFile which we split from");
		System.out.println("-T <name of out test file>");
		System.out.println("    Sets name of output test file");
		System.out.println("-t <name of output training file>");
		System.out.println("    Sets name of output training file");
		
	} 
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		
		String spl = "";
		String inputFile = "";
		String outputTrainFile = "";//Utils.getOption("-l", args);
		String outputTestFile = "";
		
		double precentage = 0.0;
		try
		{
			spl = Utils.getOption("s", args);
			inputFile = Utils.getOption("i", args);
			outputTestFile = Utils.getOption("T", args);
			outputTrainFile = Utils.getOption("t", args);
			
            //验证percentage
			if(spl.length()!=0)
			{
				try
	    		{
	    			precentage = Double.parseDouble(spl);
	    		}
	    		catch(Exception e)
	    		{
	    			throw new Exception("the percentage for the test/training set split is wrong");
	    		}
	    		if(precentage<=0 || precentage >=1)
	    		{
	    			throw new Exception("the percentage for the test/training set split should be in (0.0,1.0)!");
	    		}
			}
			else
			{
				throw new Exception("have no the percentage for the training/test set split");
			}
			
            //验证name of input file
			if(inputFile.length()==0)
	    	{
	    		throw new Exception("no input file given!");
	    	}
	    	else
	    	{
	    		if(!new File(inputFile).exists())
	    			throw new Exception("input file no found");
	    		
	    		try
	    		{
	    			Util.getInstances(inputFile);
	    		}
	    	    catch(Exception e)
	    		{
	    	    	//System.out.println(e + "xxxx");
	    		    throw new Exception(e.getMessage());
	    		}
	    	}
			
            //验证name of output train file
			if(outputTrainFile.length()==0)
	    	{
	    		throw new Exception("the name of output training file is not given!");
	    	}
	    	else
	    	{
	    		if(outputTrainFile.endsWith("arff"))
	    		{
	    			if(!new File(outputTrainFile).createNewFile())
	    				throw new Exception("the path of output training file is exsited");
	    		}
	    		else
	    		{
	    			throw new Exception("the path of output training file is wrong");
	    		}
	    		
	    	}
			
            //验证name of output test file
			if(outputTestFile.length()==0)
	    	{
	    		throw new Exception("the name of output test file is not given!");
	    	}
	    	else
	    	{
	    		if(outputTestFile.endsWith("arff"))
	    		{
	    			if(!new File(outputTestFile).createNewFile())
	    				throw new Exception("the path of output test file is exsited");
	    		}
	    		else
	    		{
	    			throw new Exception("the path of output test file is wrong");
	    		}
	    	}
			
			//切割产生testFile,trainFile;
			Instances x = Util.getInstances(inputFile);
			int length = x.numInstances();
			int i = new Double(length*precentage).intValue();
			
			Instances y = new Instances(x,0);
			for(int j = 0; j < i; j ++)
			{
				y.add(x.firstInstance());
				x.delete(0);
			}
			Spliter test = new Spliter();
			test.outputFile(x, outputTrainFile);
			test.outputFile(y, outputTestFile);
			
		}
		catch(Exception e)
		{
			System.out.println("Spliter Exception:" + e.getMessage() );
			Spliter.use();
		}
		
		
	}

}
