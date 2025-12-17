package ntu.edu.nguyenquockhanh.btl_quizzing.model;

public class Category {
    private int id;
    private String name;
    int iconResId;
    int startColor;
    int endColor;

    public Category(){}
    public Category(int id, String name, int iconResId, int startColor, int endColor) {
        this.id = id;
        this.name = name;
        this.iconResId = iconResId;
        this.startColor = startColor;
        this.endColor = endColor;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public int getStartColor() {
        return startColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public int getEndColor() {
        return endColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
