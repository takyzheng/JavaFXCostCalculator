package net.zhengtianyi;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import net.zhengtianyi.entity.FunData;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 类名 ClassName  CostCalculator
 * 项目 ProjectName  JavaFXCostCalculator
 * 作者 Author  郑添翼 Taky.Zheng
 * 邮箱 E-mail 275158188@qq.com
 * 时间 Date  2019-06-01 23:33 ＞ω＜
 * 描述 Description TODO
 */
public class CostCalculator extends Application {

    private Integer i = 0; //用于重新编排序号
    private String timeType = "天"; //用于选择天

    private ComboBox<String> cb_TimeType = new ComboBox<>(FXCollections.observableArrayList("天","周")); //用于选择时间类型
    private static TableView<FunData> tableView; //展示列表

    private Label t_l5; //每小时成本标签
    private Label lable_NumberOfPeople; //需要人数标签

    private TextField textField_Salary; //人员工资输入框
    private TextField textField_StaffCost; //人员成本比例输入框
    private TextField textField_Cost; //开发费用输入框
    private TextField textField_Period; //开发周期输入框

    private Button addBtn; //添加按钮
    private Button delBtn; //删除按钮
    private Button clearBtn; //清空按钮
    private Button calculateBtn; //计算按钮

    private static Double time = 0.0; //记录总开发小时数
    private static Double  meiDianNanDuYongShi = 0.0; //每点难度用时
    private static Double meiXiaoShiFeiYong = 0.0; //没小时费用
    private Double renYuanChengBenBiLi = 0.0; //记录人员成本比例
    private Double renYuanGongZi = 0.0; //记录人员工资
    private static Double renYuanMeiXiaoShiGongZi = 0.0; //记录人员每小时工资
    private Double suoXuRenShu = 0.0; //记录所需人数
    private static Double kaiFaZongFeiYong = 0.0; //记录开发总费用


    /**
     * 方法名 MethodName main
     * 参数 Params [args]
     * 返回值 Return void
     * 作者 Author 郑添翼 Taky.Zheng
     * 编写时间 Date 2019-06-02 13:30 ＞ω＜
     * 描述 Description TODO 主函数
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * 方法名 MethodName start
     * 参数 Params [primaryStage]
     * 返回值 Return void
     * 作者 Author 郑添翼 Taky.Zheng
     * 编写时间 Date 2019-06-02 13:30 ＞ω＜
     * 描述 Description TODO FX主窗口
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        //初始化界面
        initView(primaryStage);
        //初始化事件
        initEvent();
        //初始化监听
        initListener();

    }


    /**
     * 方法名 MethodName initView
     * 参数 Params [primaryStage]
     * 返回值 Return void
     * 作者 Author 郑添翼 Taky.Zheng
     * 编写时间 Date 2019-06-02 13:29 ＞ω＜
     * 描述 Description TODO 初始化界面
     */
    private void initView(Stage primaryStage) {
        Label t_l1 = new Label("开发周期*");
        textField_Period = new TextField();
        cb_TimeType.setValue(timeType);
        textField_Period.setPromptText("请输入开发周期");
        Label t_l2 = new Label("开发总费用*");
        textField_Cost = new TextField();
        textField_Cost.setPromptText("请输入开发费用");
        HBox.setHgrow(textField_Cost,Priority.ALWAYS);
        HBox t_fp1 = new HBox(10,t_l1, textField_Period, cb_TimeType,t_l2, textField_Cost);
        t_fp1.setAlignment(Pos.CENTER_LEFT);


        Label t_l3 = new Label("人均工资");
        textField_Salary = new TextField();
        textField_Salary.setPromptText("人均工资");
        Label t_l4 = new Label("人员成本占比");
        t_l5 = new Label("人员每月每小时成本:");
        lable_NumberOfPeople = new Label("需要人数:");
        textField_StaffCost = new TextField();
        textField_StaffCost.setMaxWidth(40);
        Label t_l41 = new Label("% 须填写开发总费用");
        HBox hBox11 = new HBox(10,t_l3, textField_Salary);
        hBox11.setAlignment(Pos.CENTER_LEFT);
        HBox hBox22 = new HBox(10,t_l4,textField_StaffCost,t_l41);
        hBox22.setAlignment(Pos.CENTER_LEFT);
        VBox t_fp2 = new VBox(10,hBox11,hBox22,t_l5, lable_NumberOfPeople);
        TitledPane tp = new TitledPane("计算用人成本",t_fp2);
        tp.setExpanded(false);
        t_fp2.setAlignment(Pos.CENTER_LEFT);



        addBtn = new Button("添加");
        delBtn = new Button("删除");
        clearBtn = new Button("清空");
        calculateBtn = new Button("计算");
        HBox t_h3 = new HBox(10,  tp,addBtn, delBtn,clearBtn, calculateBtn);
        HBox.setHgrow(tp, Priority.ALWAYS);
        t_h3.setAlignment(Pos.CENTER_RIGHT);
        VBox t_v1 = new VBox(10,t_fp1,t_h3);
        t_v1.setPadding(new Insets(10));


        TableColumn<FunData, Integer> t1 = new TableColumn<>("序号");
        TableColumn<FunData, String> t2 = new TableColumn<>("功能模块");
        TableColumn<FunData, Integer> t3 = new TableColumn<>("开发难度");
        TableColumn<FunData, Double> t4 = new TableColumn<>("开发时间(h)");
        TableColumn<FunData, Double> t5 = new TableColumn<>("开发费用(¥)");
        TableColumn<FunData, Boolean> t6 = new TableColumn<>("选择");

        t1.setCellValueFactory(new PropertyValueFactory<>("ID"));
        t2.setCellValueFactory(new PropertyValueFactory<>("gongNengMokuai"));
        t3.setCellValueFactory(new PropertyValueFactory<>("kaiFaNanDu"));
        t4.setCellValueFactory(new PropertyValueFactory<>("kaiFaShiJian"));
        t5.setCellValueFactory(new PropertyValueFactory<>("kaiFaFeiYong"));
        t6.setCellValueFactory(new PropertyValueFactory<>("select"));

        t2.setCellFactory(TextFieldTableCell.forTableColumn());
        t3.setCellFactory(ComboBoxTableCell.forTableColumn(1,2,3,4,5,6,7,8,9,10));
        t4.setCellFactory(param -> new DoubleCell());
        t5.setCellFactory(param -> new DoubleCell());
        t6.setCellFactory(CheckBoxTableCell.forTableColumn(t6));


        tableView = new TableView<>();
        tableView.getColumns().addAll(t1,t2,t3,t4,t5,t6);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setTableMenuButtonVisible(true);
        tableView.setEditable(true);
        BorderPane root  = new BorderPane();
        root.setPadding(new Insets(10));
        root.setTop(t_v1);
        root.setCenter(tableView);
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("成本计算器1.0 Taky QQ:275158188");
        primaryStage.setWidth(650);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    /**
     * 方法名 MethodName initEvent
     * 参数 Params []
     * 返回值 Return void
     * 作者 Author 郑添翼 Taky.Zheng
     * 编写时间 Date 2019-06-02 13:28 ＞ω＜
     * 描述 Description TODO 初始化事件
     */
    private void initEvent(){
        addBtn.setOnAction(p ->{
            AddDialog addDialog = new AddDialog();
            Optional<ButtonType> buttonType = addDialog.showAndWait();
            buttonType.ifPresent(p1 ->{
                if (p1.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                    FunData data = addDialog.getData();
                    data.setID(tableView.getItems().size() + 1);
                    tableView.getItems().add(data);
                }
            });
            jiSuan();
        });

        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                System.out.println(newValue.getID() + " " + newValue.getGongNengMokuai() + " " + newValue.getKaiFaNanDu() + " " + newValue.isSelect());
            }
        });

        //删除事件
        delBtn.setOnAction(p -> {
            i = 1;
            List<FunData> collect = tableView.getItems().filtered(p1 -> !p1.isSelect()).stream().collect(Collectors.toList());
            collect.forEach(p1-> p1.setID(i++));
            ObservableList<FunData> templist = FXCollections.observableArrayList(collect);
            tableView.getItems().clear();
            tableView.setItems(templist);
            jiSuan();
            tableView.refresh();
        });

        //清空数据
        clearBtn.setOnAction(p ->{
            textField_Salary.clear();
            textField_StaffCost.clear();
            textField_Cost.clear();
            textField_Period.clear();

            t_l5.setText("");
            lable_NumberOfPeople.setText("");

            time = 0.0;
            meiDianNanDuYongShi = 0.0;
            meiXiaoShiFeiYong = 0.0;
            renYuanChengBenBiLi = 0.0;
            renYuanGongZi = 0.0;
            renYuanMeiXiaoShiGongZi = 0.0;
            suoXuRenShu = 0.0;
            kaiFaZongFeiYong = 0.0;

            tableView.getItems().clear();

        });

        //计算按钮
        calculateBtn.setOnAction(p -> jiSuan());
    }


    /**
     * 方法名 MethodName initListener
     * 参数 Params []
     * 返回值 Return void
     * 作者 Author 郑添翼 Taky.Zheng
     * 编写时间 Date 2019-06-02 13:13 ＞ω＜
     * 描述 Description TODO 初始化监听
     */
    private void initListener(){
        //开发周期文本监听
        textField_Period.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) time = 0.0;
            if (newValue != null && !newValue.trim().isEmpty()) {
                Integer temp = Integer.valueOf(newValue);
                switch (timeType) {
                    case "天":
                        time = temp * 8.0;
                        break;
                    case "周":
                        time = temp * 7.0 * 8.0;
                        break;
                }
                getMeiXiaoShiFeiYong();
                getSuoXuRenShu();
            }
        });
        //给开发周期添加文本过滤
        textField_Period.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.isDeleted()) return change;
            if (change.getText().matches("[0-9]")){
                return change;
            }
            return null;
        }));
        //开发周期选择监听
        cb_TimeType.valueProperty().addListener((observable, oldValue, newValue) -> {
            timeType = newValue;
            if (time != null || time != 0) {
                if ("天".equals(timeType)){
                    time = time / 7.0;
                } else if ("周".equals(timeType)) {
                    time = time * 7.0;
                }
            }
        });
        //开发费用监听
        textField_Cost.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) time = 0.0;
            if (newValue != null && !newValue.trim().isEmpty()) {
                kaiFaZongFeiYong = Double.valueOf(newValue);
                getMeiXiaoShiFeiYong();
                getSuoXuRenShu();
            }
        });
        //给开发周期添加文本过滤
        textField_Cost.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.isDeleted()) return change;
            if (change.getText().matches("[0-9]")){
                return change;
            }
            return null;
        }));

        //人员工资监听
        textField_Salary.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) renYuanGongZi = 0.0;
            if (newValue != null && !newValue.trim().isEmpty()) {
                renYuanGongZi = Double.valueOf(newValue);
                getRenYuanMeiXiaoShiFeiYong();
                getSuoXuRenShu();
            }
        });
        //开发费用添加文本过滤
        textField_Salary.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.isDeleted()) return change;
            if (change.getText().matches("[0-9]")){
                return change;
            }
            return null;
        }));
        //成本比例监听
        textField_StaffCost.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() == 0) renYuanChengBenBiLi = 0.0;
            if (newValue.length() > 2) textField_StaffCost.setText(oldValue);
            if (newValue != null && !newValue.trim().isEmpty()) {
                renYuanChengBenBiLi = Double.valueOf(newValue) / 100;
                getSuoXuRenShu();
            }
        });
        textField_StaffCost.setTextFormatter(new TextFormatter<String>(change -> {
            if (change.isDeleted()) return change;
            if (change.getText().matches("[0-9]")){
                return change;
            }
            return null;
        }));
    }

    /**
     * 方法名 MethodName getMeiXiaoShiFeiYong
     * 参数 Params []
     * 返回值 Return void
     * 作者 Author 郑添翼 Taky.Zheng
     * 编写时间 Date 2019-06-02 13:32 ＞ω＜
     * 描述 Description TODO 获取每小时费用
     */
    public void getMeiXiaoShiFeiYong(){
        BigDecimal bigKFFY = new BigDecimal(kaiFaZongFeiYong.toString());
        BigDecimal bigTIME = new BigDecimal(time.toString());
        if (bigTIME.doubleValue() != 0 ){
            meiXiaoShiFeiYong =  bigKFFY.divide(bigTIME,2).doubleValue();
        }
    }

    /**
     * 方法名 MethodName getRenYuanMeiXiaoShiFeiYong
     * 参数 Params []
     * 返回值 Return void
     * 作者 Author 郑添翼 Taky.Zheng
     * 编写时间 Date 2019-06-02 18:28 ＞ω＜
     * 描述 Description TODO 人员工资平均每小时费用
     */
    public void getRenYuanMeiXiaoShiFeiYong(){
        renYuanMeiXiaoShiGongZi = renYuanGongZi / 240;
        t_l5.setText("人员每小时成本: " + String.format("%.2f",renYuanMeiXiaoShiGongZi) + " ¥");
    }

    /**
     * 方法名 MethodName getSuoXuRenShu
     * 参数 Params []
     * 返回值 Return void
     * 作者 Author 郑添翼 Taky.Zheng
     * 编写时间 Date 2019-06-02 13:31 ＞ω＜
     * 描述 Description TODO 获取所需人数
     */
    public void getSuoXuRenShu(){
        BigDecimal bigKFFY = new BigDecimal(kaiFaZongFeiYong.toString());
        BigDecimal bigRYBL = new BigDecimal(renYuanChengBenBiLi.toString());
        BigDecimal bigTIME = new BigDecimal(time.toString());
        BigDecimal bigRYMXSGZ = new BigDecimal(renYuanMeiXiaoShiGongZi.toString());
        if (bigTIME.doubleValue() != 0 && bigRYMXSGZ.doubleValue() != 0) {
            suoXuRenShu = bigKFFY.multiply(bigRYBL).divide(bigTIME, 2).divide(bigRYMXSGZ, 2).doubleValue();
            lable_NumberOfPeople.setText("需要人数: " + String.format("%.2f", suoXuRenShu) + " 人");
        }
    }

    /**
     * 方法名 MethodName jiSuan
     * 参数 Params []
     * 返回值 Return void
     * 作者 Author 郑添翼 Taky.Zheng
     * 编写时间 Date 2019-06-02 13:31 ＞ω＜
     * 描述 Description TODO 计算方法
     */
    public static void jiSuan(){
        Double temp = 0.0; //记录总开发难度
        for (FunData fd : tableView.getItems()){
            temp += fd.getKaiFaNanDu();
        }
        meiDianNanDuYongShi = time / temp; //计算出每点难度用时
        for (FunData fd :tableView.getItems()){
            Double kaiFaShijian = meiDianNanDuYongShi * fd.getKaiFaNanDu();
            Double kaiFaFeiYong = meiXiaoShiFeiYong * kaiFaShijian;
            if (kaiFaFeiYong > kaiFaZongFeiYong) kaiFaFeiYong = kaiFaZongFeiYong;
            fd.setKaiFaShiJian(kaiFaShijian);
            fd.setKaiFaFeiYong(kaiFaFeiYong);
        }
    }

    //转换为保留两位小数字符串形式(四舍五入)
    class DoubleCell extends TableCell<FunData, Double>{
        @Override
        protected void updateItem(Double item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
                setText(String.format("%.2f",item));
            }
        }
    }
}
