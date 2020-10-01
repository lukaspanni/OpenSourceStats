package com.example.opensoucestats.client;

import java.util.Date;
import java.util.List;

public interface Client {

    int getCommits(Date start, Date end);
    int getIssues(Date start, Date end);
    int getPullRequests(Date start, Date end);
    int getPullRequestReviews(Date start, Date end);
    int getAllContributions(Date start, Date end);


    List<String> getCommitRepositories(Date start, Date end);
    List<String> getIssueRepositories(Date start, Date end);
    List<String> getPullRequestRepositories(Date start, Date end);
    List<String> getPullRequestReviewRepositories(Date start, Date end);
    List<String> getAllContributionsRepositories(Date start, Date end);

}

