import java.util.Properties;
import java.io.*;
class Settings {
    private Properties props;
    private String propsFileName;
    private String universalDefault;
    
    public String getPropsFileName() {
        return propsFileName;
    }
    
    private void save() {
        try {
            File propsFile = new File(propsFileName);
            if (!propsFile.exists()) {
                propsFile.createNewFile();
            }
            
            FileOutputStream outputStream = new FileOutputStream(propsFileName);
            props.store(outputStream, "");
            outputStream.close();
        } catch (Exception e) {
            System.err.println("Failed to save settings!");
            e.printStackTrace();
        }
    }
    
    public void reset() {
        props = new Properties();
        save();
    }
    
    public void loadFromFile(String fileName) {
        propsFileName = fileName;
        props = new Properties(props);
        try {
            props.load(new FileInputStream(fileName));
        } catch (Exception e) {
            System.err.println("Failed to load saved settings!");
            e.printStackTrace();
        }
    }
    public void loadFromResource(String resourceName) {
        props = new Properties(props);
        try {
            props.load(Settings.class.getResourceAsStream(resourceName));
        } catch (Exception e) {
            System.err.println("Failed to load saved resource settings!");
            e.printStackTrace();
        }
    }
    public void setUniversalDefault(String uDefault) {
        universalDefault = uDefault;
    }
    
    public String get(String key, String thisDefault) {
       String got = props.getProperty(key);
       if (got == null) {
           if (thisDefault != null) {
               set(key, thisDefault);
               return thisDefault;
           } else {
               set(key, universalDefault);
               return universalDefault;
           }
       } else {
           return got;
       }
    }
    public String get(String key) {
        return get(key, null);
    }
    public void set(String key, String value) {
        props.setProperty(key, value);
        save();
    }
}