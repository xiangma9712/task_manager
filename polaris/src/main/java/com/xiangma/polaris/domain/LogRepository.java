package com.xiangma.polaris.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    public List<Log> findByTask(Task task);

    public List<Log> findByTaskIn(List<Task> tasks);
}
