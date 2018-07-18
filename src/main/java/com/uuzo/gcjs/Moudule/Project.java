package com.uuzo.gcjs.Moudule;

import java.util.List;

/**
 * Created by jie on 2018/7/17.
 */

public class Project {

    /**
     * status : true
     * content : [{"id":126,"project_name":"工程1","project_type":2,"ptype":0,"project_number":"","action_unit":"","start_time":"2018-07-03","end_time":"2018-10-04","work_day":94,"plan":0},{"id":127,"project_name":"工程2","project_type":1,"ptype":0,"project_number":"","action_unit":"","start_time":"2018-07-06","end_time":"2019-01-04","work_day":183,"plan":0},{"id":129,"project_name":"工程123","project_type":1,"ptype":1,"project_number":"A468448","action_unit":"创辉","start_time":"2019-05-06","end_time":"2019-05-06","work_day":335,"plan":0}]
     */

    private boolean status;
    private List<ContentBean> content;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * id : 126
         * project_name : 工程1
         * project_type : 2
         * ptype : 0
         * project_number :
         * action_unit :
         * start_time : 2018-07-03
         * end_time : 2018-10-04
         * work_day : 94
         * plan : 0
         */

        private int id;
        private String project_name;
        private int project_type;
        private int ptype;
        private String project_number;
        private String action_unit;
        private String start_time;
        private String end_time;
        private int work_day;
        private int plan;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }

        public int getProject_type() {
            return project_type;
        }

        public void setProject_type(int project_type) {
            this.project_type = project_type;
        }

        public int getPtype() {
            return ptype;
        }

        public void setPtype(int ptype) {
            this.ptype = ptype;
        }

        public String getProject_number() {
            return project_number;
        }

        public void setProject_number(String project_number) {
            this.project_number = project_number;
        }

        public String getAction_unit() {
            return action_unit;
        }

        public void setAction_unit(String action_unit) {
            this.action_unit = action_unit;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public int getWork_day() {
            return work_day;
        }

        public void setWork_day(int work_day) {
            this.work_day = work_day;
        }

        public int getPlan() {
            return plan;
        }

        public void setPlan(int plan) {
            this.plan = plan;
        }
    }
}
