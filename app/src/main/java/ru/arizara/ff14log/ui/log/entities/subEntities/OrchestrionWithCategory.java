package ru.arizara.ff14log.ui.log.entities.subEntities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;
import java.util.Objects;

import ru.arizara.ff14log.ui.log.entities.Orchestrion;

public class OrchestrionWithCategory {
    @Embedded
    private Orchestrion orchestrion;


    @Relation(parentColumn = "categoryID", entityColumn = "id")
    private List<CategoryLog> categoryLog;

    public Orchestrion getOrchestrion() {
        return orchestrion;
    }

    public void setOrchestrion(Orchestrion orchestrion) {
        this.orchestrion = orchestrion;
    }

    public List<CategoryLog> getCategoryLog() {
        return categoryLog;
    }

    public void setCategoryLog(List<CategoryLog> categoryLog) {
        this.categoryLog = categoryLog;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrchestrionWithCategory that = (OrchestrionWithCategory) o;
        return orchestrion.equals(that.orchestrion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orchestrion);
    }
}
