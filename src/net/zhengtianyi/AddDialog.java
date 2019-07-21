package net.zhengtianyi;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import net.zhengtianyi.entity.FunData;

/**
 * 类名 ClassName  AddDialog
 * 项目 ProjectName  JavaFXCostCalculator
 * 作者 Author  郑添翼 Taky.Zheng
 * 邮箱 E-mail 275158188@qq.com
 * 时间 Date  2019-06-02 08:36 ＞ω＜
 * 描述 Description TODO
 */
public class AddDialog extends Dialog<ButtonType> {

    private TextField t1;
    private Integer nanDu = 5;
    private ComboBox<Integer> comboBox = new ComboBox<>(FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10));
    public AddDialog() {
        initView();
    }


    private void initView(){

        Label l1 = new Label("模块名称");
        t1 = new TextField();
        t1.setPromptText("模块名称");
        Label l2 = new Label("开发难度");
        HBox h1 = new HBox(10,l1,t1,l2,comboBox);
        h1.setAlignment(Pos.CENTER);
        comboBox.setValue(nanDu);

        ButtonType ok = new ButtonType("确认", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("取消", ButtonBar.ButtonData.CANCEL_CLOSE);
        DialogPane dialogPane = new DialogPane();
        dialogPane.setContent(h1);
        dialogPane.getButtonTypes().addAll(cancel, ok);
        setDialogPane(dialogPane);

        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nanDu = newValue;
            }
        });
    }

    /**
     * 方法名 MethodName getData
     * 参数 Params []
     * 返回值 Return net.zhengtianyi.entity.FunData
     * 作者 Author 郑添翼 Taky.Zheng
     * 编写时间 Date 2019-06-02 09:00 ＞ω＜
     * 描述 Description TODO 返回数据
     */
    public FunData getData(){
        return new FunData(t1.getText(),nanDu);
    }

    /**
     * 方法名 MethodName clearText
     * 参数 Params []
     * 返回值 Return void
     * 作者 Author 郑添翼 Taky.Zheng
     * 编写时间 Date 2019-06-02 09:04 ＞ω＜
     * 描述 Description TODO 清楚文本框内容
     */
    public void clearText(){
        t1.setText("");
        nanDu = 0;
    }


}
