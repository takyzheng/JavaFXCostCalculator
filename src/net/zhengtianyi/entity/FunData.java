package net.zhengtianyi.entity;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import net.zhengtianyi.CostCalculator;

/**
 * 类名 ClassName  FunData
 * 项目 ProjectName  JavaFXCostCalculator
 * 作者 Author  郑添翼 Taky.Zheng
 * 邮箱 E-mail 275158188@qq.com
 * 时间 Date  2019-06-02 00:01 ＞ω＜
 * 描述 Description TODO
 */
public class FunData {

    private SimpleIntegerProperty ID = new SimpleIntegerProperty();     //序号
    private SimpleStringProperty gongNengMokuai = new SimpleStringProperty();  //功能模块
    private SimpleIntegerProperty kaiFaNanDu = new SimpleIntegerProperty(); //开发难度
    private SimpleDoubleProperty kaiFaShiJian = new SimpleDoubleProperty(); //开发时间
    private SimpleDoubleProperty kaiFaFeiYong = new SimpleDoubleProperty(); //开发费用

    private SimpleBooleanProperty select = new SimpleBooleanProperty(false);

    public FunData(String gongNengMokuai,Integer kaiFaNanDu){
        if (gongNengMokuai.trim().isEmpty()) gongNengMokuai = "未命名";
        this.gongNengMokuai.set(gongNengMokuai);
        this.kaiFaNanDu.set(kaiFaNanDu);

        this.kaiFaNanDuProperty().addListener((observable, oldValue, newValue) -> {
            CostCalculator.jiSuan();
        });
    }


    public int getID() {
        return ID.get();
    }

    public SimpleIntegerProperty IDProperty() {
        return ID;
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public String getGongNengMokuai() {
        return gongNengMokuai.get();
    }

    public SimpleStringProperty gongNengMokuaiProperty() {
        return gongNengMokuai;
    }

    public void setGongNengMokuai(String gongNengMokuai) {
        this.gongNengMokuai.set(gongNengMokuai);
    }

    public int getKaiFaNanDu() {
        return kaiFaNanDu.get();
    }

    public SimpleIntegerProperty kaiFaNanDuProperty() {
        return kaiFaNanDu;
    }

    public void setKaiFaNanDu(int kaiFaNanDu) {
        this.kaiFaNanDu.set(kaiFaNanDu);
    }

    public double getKaiFaShiJian() {
        return kaiFaShiJian.get();
    }

    public SimpleDoubleProperty kaiFaShiJianProperty() {
        return kaiFaShiJian;
    }

    public void setKaiFaShiJian(double kaiFaShiJian) {
        this.kaiFaShiJian.set(kaiFaShiJian);
    }

    public double getKaiFaFeiYong() {
        return kaiFaFeiYong.get();
    }

    public SimpleDoubleProperty kaiFaFeiYongProperty() {
        return kaiFaFeiYong;
    }

    public void setKaiFaFeiYong(double kaiFaFeiYong) {
        this.kaiFaFeiYong.set(kaiFaFeiYong);
    }

    public boolean isSelect() {
        return select.get();
    }

    public SimpleBooleanProperty selectProperty() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select.set(select);
    }


}
