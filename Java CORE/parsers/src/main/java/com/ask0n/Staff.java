package com.ask0n;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "staff")
@XmlAccessorType(XmlAccessType.FIELD)
public class Staff
{
    @XmlElement(name = "employee")
    public List<Employee> staff;

    public List<Employee> getStaff() {
        return staff;
    }

    public void setStaff(List<Employee> employee) {
        this.staff = employee;
    }
}
