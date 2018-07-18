package com.uuzo.gcjs.Moudule;

import java.util.List;

/**
 * Created by jie on 2018/7/17.
 */

public class Member {

    /**
     * status : true
     * content : [{"id":210,"user_name":"admin","job":"总经理/副总/总工","jid":1,"department":"经营班子","unit_name":"惠州市供电局电器安装公司","tel":"15914905652","e_mail":"1436899717@qq.com"}]
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
         * id : 210
         * user_name : admin
         * job : 总经理/副总/总工
         * jid : 1
         * department : 经营班子
         * unit_name : 惠州市供电局电器安装公司
         * tel : 15914905652
         * e_mail : 1436899717@qq.com
         */

        private int id;
        private String user_name;
        private String job;
        private int jid;
        private String department;
        private String unit_name;
        private String tel;
        private String e_mail;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public int getJid() {
            return jid;
        }

        public void setJid(int jid) {
            this.jid = jid;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getE_mail() {
            return e_mail;
        }

        public void setE_mail(String e_mail) {
            this.e_mail = e_mail;
        }
    }
}
