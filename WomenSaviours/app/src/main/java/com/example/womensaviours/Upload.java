package com.example.womensaviours;

class Upload {
    String s,c,ctype;

    public Upload(String s, String c, String ctype) {
        this.s = s;
        this.c = c;
        this.ctype = ctype;
    }
    public Upload(){}

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }


}
