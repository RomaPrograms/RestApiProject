package by.yellow.testtask.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
class BaseEntity {

    @Id
    private int id;
}
