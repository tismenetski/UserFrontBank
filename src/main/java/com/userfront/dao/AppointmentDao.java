package com.userfront.dao;

import com.userfront.domain.Appointment;
import com.userfront.domain.PrimaryTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AppointmentDao extends CrudRepository<Appointment,Long> {

    List<Appointment> findAll();

    //Appointment findById(Long id);
    //Appointment findOne(Long id);

}
