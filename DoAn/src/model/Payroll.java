/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author lnminh
 */
public class Payroll {
   private String IDEmp;
   private String nameEmp;
   private Date time;
   private int baseSalary;
   private int bonus;
   private int allowance;
   private int dayOff;
   private int realSalary;

    public Payroll() {
    }

    public Payroll(String IDEmp, String nameEmp, Date time, int baseSalary, int bonus, int allowance, int dayOff, int realSalary) {
        this.IDEmp = IDEmp;
        this.nameEmp = nameEmp;
        this.time = time;
        this.baseSalary = baseSalary;
        this.bonus = bonus;
        this.allowance = allowance;
        this.dayOff = dayOff;
        this.realSalary = realSalary;
    }

    public String getIDEmp() {
        return IDEmp;
    }

    public void setIDEmp(String IDEmp) {
        this.IDEmp = IDEmp;
    }

    public String getNameEmp() {
        return nameEmp;
    }

    public void setNameEmp(String nameEmp) {
        this.nameEmp = nameEmp;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(int baseSalary) {
        this.baseSalary = baseSalary;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public int getAllowance() {
        return allowance;
    }

    public void setAllowance(int allowance) {
        this.allowance = allowance;
    }

    public int getDayOff() {
        return dayOff;
    }

    public void setDayOff(int dayOff) {
        this.dayOff = dayOff;
    }

    public int getRealSalary() {
        return realSalary;
    }

    public void setRealSalary(int realSalary) {
        this.realSalary = realSalary;
    }
    
}
