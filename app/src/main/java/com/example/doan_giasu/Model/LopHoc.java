package com.example.doan_giasu.Model;

public class LopHoc {
    private int MaLop;
    private String Title;
    private float HocPhi;
    private String MonHoc;
    private String ThoiGian;

    public LopHoc(int maLop, String title, float hocPhi, String monHoc, String thoiGian, String diaDiem) {
        MaLop = maLop;
        Title = title;
        HocPhi = hocPhi;
        MonHoc = monHoc;
        ThoiGian = thoiGian;
    }

    public int getMaLop() {
        return MaLop;
    }

    public void setMaLop(int maLop) {
        MaLop = maLop;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public float getHocPhi() {
        return HocPhi;
    }

    public void setHocPhi(float hocPhi) {
        HocPhi = hocPhi;
    }

    public String getMonHoc() {
        return MonHoc;
    }

    public void setMonHoc(String monHoc) {
        MonHoc = monHoc;
    }

    public String getThoiGian() {
        return ThoiGian;
    }

    public void setThoiGian(String thoiGian) {
        ThoiGian = thoiGian;
    }

}
