package zerogreen.eco.entity.userentity;

public enum VegetarianGrade {
    VEGAN("비건"), LACTO("락토"), OVO("오보"),
    LACTO_OVO("락토-오보"), PESCO("페스코"), POLLO("폴로"), PLEXITARIAN("플렉시타리안");

    private final String vegetarian;

    VegetarianGrade(String vegetarian) {
        this.vegetarian = vegetarian;
    }

    public String getVegetarian() {
        return vegetarian;
    }
}
