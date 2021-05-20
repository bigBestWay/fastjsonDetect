import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;

public class FastjsonDetectAgent {
    private static String log_file = "/tmp/fastjson_detect.log";
    public static void agentmain(String args, Instrumentation inst) throws Exception 
    {
        try {
            @SuppressWarnings("rawtypes")
            Class[] classes = inst.getAllLoadedClasses();
            for (Class clazz : classes)
            {
                String clazzName = clazz.getName();
                if(clazzName.endsWith("com.alibaba.fastjson.JSON"))
                {
                    URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
                    String jarFile = URLDecoder.decode(url.getPath(), "utf-8");// 转化为utf-8编码，支持中文
                    File file = new File(jarFile);
                    jarFile = file.getAbsolutePath();
                    writeLog(clazzName + " of " + jarFile);
                    
                    try{
                        Field field = clazz.getField("VERSION");
                        String ver = String.valueOf(field.get(clazz));
                        writeLog("****fastjson VERSION = " + ver);
                    }catch (Exception e) {
                        writeLog(e.toString());
                        writeLog(e.getStackTrace().toString());
                    }
                }
                else if(clazzName.endsWith("com.alibaba.fastjson.parser.ParserConfig"))
                {
                    URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
                    String jarFile = URLDecoder.decode(url.getPath(), "utf-8");// 转化为utf-8编码，支持中文
                    File file = new File(jarFile);
                    jarFile = file.getAbsolutePath();
                    writeLog(clazzName + " of " + jarFile);
                    
                    try{
                        Object obj = clazz.getField("global").get(clazz);
                        Method getAutoType = clazz.getMethod("isAutoTypeSupport");
                        String isAutotypeSupport = String.valueOf(getAutoType.invoke(obj));
                        writeLog("****fastjson isAutoTypeSupport = " + isAutotypeSupport);
                    }catch (Exception e) {
                        writeLog(e.toString());
                        writeLog(e.getStackTrace().toString());
                    }
                }
            }
        }
        catch (Exception e) {
            writeLog(e.toString());
            writeLog(e.getStackTrace().toString());
        }
    }
    
    private static void writeLog(String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件,如果为 true，则将字节写入文件末尾处，而不是写入文件开始处 
            FileWriter writer = new FileWriter(log_file, true);
            writer.write(content + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void premain(String args, Instrumentation inst) throws Exception
    {
        System.out.println("Pre Args:" + args);
        Class[] classes = inst.getAllLoadedClasses();
        for (Class clazz : classes) 
        {
           System.out.println(clazz.getName());
        }
    } 
}
