package org.seckill.JmeterParams;

import com.csvreader.CsvWriter;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Author Zhuang YeFan
 * @Date    2018-3-6
 * @Description  生成测试excution的参数csv文件   seckillId，userphone
 **/
public class ExecutionData {
    static Long userPhone1000 = 15150680000L;
    static Long userPhone2000 = 15250680000L;
    private static void OneThousand(){
        String csvFilePath = "C:\\Users\\zhuangyefan\\Desktop\\Graduation\\Jmeter\\10000-Execution.csv";
        // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);
        CsvWriter csvWriter = new CsvWriter(csvFilePath,',', Charset.forName("UTF-8"));
        for (int i = 0; i < 10000; i++) {
            String[] csvContent = {"4" , userPhone1000 + ""};
            System.out.println(csvContent.toString());
            try {
                csvWriter.writeRecord(csvContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
            userPhone1000 ++;
        }
        csvWriter.close();
    }

    private static void TwoThousands(){
        String csvFilePath = "C:\\Users\\zhuangyefan\\Desktop\\Graduation\\Jmeter\\2000-Execution.csv";
        // 创建CSV写对象 例如:CsvWriter(文件路径，分隔符，编码格式);
        CsvWriter csvWriter = new CsvWriter(csvFilePath,',', Charset.forName("UTF-8"));
        for (int i = 0; i < 10000; i++) {
            String[] csvContent = {"4" , userPhone2000 + ""};
            System.out.println(csvContent.toString());
            try {
                csvWriter.writeRecord(csvContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
            userPhone2000 ++;
        }
        csvWriter.close();
    }

    public static void main(String args[]){
        OneThousand();
//        TwoThousands();
    }
}
