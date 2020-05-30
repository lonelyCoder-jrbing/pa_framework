import com.skyscraper.saas.mask.annotation.Desensitized;
import com.skyscraper.saas.mask.enums.SensitiveTypeEnum;

/**
 * create by sumerian on 2020/5/31
 * <p>
 * desc:
 **/
public class Test {

    @Desensitized(type=SensitiveTypeEnum.CHINESE_NAME)
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Test{" +
                "name='" + name + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {

        Test test = new Test();
        test.setName("巨熔冰");
        System.out.println(test);




    }


}
