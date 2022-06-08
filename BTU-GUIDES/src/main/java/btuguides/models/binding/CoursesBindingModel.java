package btuguides.models.binding;

import btuguides.models.entity.Workers;

public class CoursesBindingModel {
    private String name;
    private String description;
    private String url;
    private String worker;
    private String workerUrl;
    private String isHere;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getWorkerUrl() {
        return workerUrl;
    }

    public void setWorkerUrl(String workerUrl) {
        this.workerUrl = workerUrl;
    }

    public String getIsHere() {
        return isHere;
    }

    public void setIsHere(String isHere) {
        this.isHere = isHere;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
