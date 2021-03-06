import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class problem3 {
	private int max = 0;


  public static class TokenizerMapper
       extends Mapper<Object, Text, Text, IntWritable>{

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
		String line = value.toString();
		String[] arr = line.split(" ");
		word.set(arr[6]);
		context.write(word, one);
		
    }

  }

  public static class IntSumReducer
       extends Reducer<Text,IntWritable,Text,IntWritable> {
    //private IntWritable result = new IntWritable();
	int max = 0;
	Text t = new Text("");

    public void reduce(Text key, Iterable<IntWritable> values,
                       Context context
                       ) throws IOException, InterruptedException {
      
	  
	  int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
	  
	  if(sum>max){
		  max = sum;
		  t.set(key);
	  }
	  
    }
	
	protected void cleanup(Context context) throws IOException, InterruptedException {
		context.write(t, new IntWritable(max));
	}
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "problem3");
    job.setJarByClass(problem3.class);
    job.setMapperClass(TokenizerMapper.class);
    //job.setCombinerClass(IntSumReducer.class);
	job.setNumReduceTasks(1);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);

  }
}