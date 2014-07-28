package com.chncwang.easy2db;

public class GithubRepos {
    public static GithubRepo fool2048() {
        final GithubRepo repo = new GithubRepo();
        repo.setId("abc");
        repo.setUrl("https://github.com/chncwang/fool2048");
        repo.setName("fool2048");
        repo.setStarCount(1);
        return repo;
    }
}
