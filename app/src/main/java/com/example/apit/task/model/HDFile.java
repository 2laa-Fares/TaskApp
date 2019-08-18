package com.example.apit.task.model;

/**
 * Created with IntelliJ IDEA.
 * User: ServusKevin
 * Date: 10/27/13
 * Time: 10:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class HDFile {

    private String Id;
    private String Name;
    private boolean Selected;
    private String FilePath;
    private String Size;
    private String Url;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public boolean isSelected() {
        return Selected;
    }

    public void setSelected(boolean selected) {
        Selected = selected;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
