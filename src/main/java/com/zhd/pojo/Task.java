package com.zhd.pojo;



public class Task {
    private Integer id;

    private String name;

    private Byte type;

    private String uid;

//    private BigDecimal startLocationX;
//
//    private BigDecimal startLocationY;
//
//    private BigDecimal endLocationX;
//
//    private BigDecimal endLocationY;

    private Long bid;

    private Boolean status;

    private String startTime;

    private String endTime;

    public Integer getId() {
        return id;
    }

    public Task setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Task setName(String name) {
        this.name = name == null ? null : name.trim();
        return this;
    }

    public Byte getType() {
        return type;
    }

    public Task setType(Byte type) {
        this.type = type;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public Task setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
        return this;
    }

//    public BigDecimal getStartLocationX() {
//        return startLocationX;
//    }
//
//    public Task setStartLocationX(BigDecimal startLocationX) {
//        this.startLocationX = startLocationX;
//        return this;
//    }
//
//    public BigDecimal getStartLocationY() {
//        return startLocationY;
//    }
//
//    public Task setStartLocationY(BigDecimal startLocationY) {
//        this.startLocationY = startLocationY;
//        return this;
//    }
//
//    public BigDecimal getEndLocationX() {
//        return endLocationX;
//    }
//
//    public Task setEndLocationX(BigDecimal endLocationX) {
//        this.endLocationX = endLocationX;
//        return this;
//    }
//
//    public BigDecimal getEndLocationY() {
//        return endLocationY;
//    }
//
//    public Task setEndLocationY(BigDecimal endLocationY) {
//        this.endLocationY = endLocationY;
//        return this;
//    }

    public Long getBid() {
        return bid;
    }

    public Task setBid(Long bid) {
        this.bid = bid;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public Task setStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public Task setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public Task setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public static String[] getKeys(){
        return new String[]{"id", "name", "type","status","uid", "bid", "startTime","endTime"};
    }

    public static String[] getNames(){
        return new String[]{"编号","任务名称","任务类型","任务状态","操作人", "处理车辆","创建时间","完成时间"};
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", uid='" + uid + '\'' +
//                ", startLocationX=" + startLocationX +
//                ", startLocationY=" + startLocationY +
//                ", endLocationX=" + endLocationX +
//                ", endLocationY=" + endLocationY +
                ", bid='" + bid + '\'' +
                ", status=" + status +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}