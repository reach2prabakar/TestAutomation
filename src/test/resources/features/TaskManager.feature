@ui @Login @TaskManagerValidation @Regression
Feature: Jira-Feature# Task Manager Validation
  As a user
  I will login to the task manager page
  So that I can manage the tasks

  @validateLogin @validateLandingPage
  Scenario: Users wants to login to the application
    Given user opens task manager login page
    When user login to the application
    Then user is able to login and view the landing page

  @ValidateAddRemoveTask
  Scenario: Users want that the task should be added or removed from the task card table

    Given user opens task manager login page
    When user login to the application
    And user is able to add a task with with title,description and date
      | taskTitle | description                  | important |
      | AutoTest1 | Sample for automation test 1 | no        |
      | AutoTest2 | Sample for automation test 2 | no        |
      | AutoTest3 | Sample for automation test 2 | no        |
      | [blank]   | Sample desc without title    | no        |
    Then user is able to see the task in task list table
      | taskTitle |
      | AutoTest3 |
      | AutoTest2 |
    Then user is able to remove a task with taskTitle
      | taskTitle |
      | AutoTest3 |


  @ValidateTaskAsDone
  Scenario: Users want that the task should be marked/unmarked a task as done from the task card table

    Given user opens task manager login page
    When user login to the application
    And user is able to add a task with with title,description and date
      | taskTitle | description                  | important |
      | AutoTest1 | Sample for automation test 1 | no        |
      | AutoTest2 | Sample for automation test 2 | no        |
      | AutoTest3 | Sample for automation test 2 | no        |
    Then user is able to mark/unMark the task as done
      | markTask  | unMarkTask |
      | AutoTest1 | AutoTest3  |


  @ValidateAllTaskPage
  Scenario: Users want all the task to display in All task page

    Given user opens task manager login page
    When user login to the application
    And user is able to add a task with with title,description and date
      | taskTitle | description                  | important |
      | AutoTest1 | Sample for automation test 1 | yes       |
      | AutoTest2 | Sample for automation test 2 | no        |
      | AutoTest3 | Sample for automation test 2 | no        |
    Then user is able to mark/unMark the task as done
      | markTask  | unMarkTask |
      | AutoTest1 | AutoTest3  |
    Then user is able to see all the new and updated task in all task page
      | taskTitle   |
      | Test Task 1 |
      | AutoTest1   |
      | AutoTest2   |
      | AutoTest3   |

  @ValidateImportantTaskPage
  Scenario: Users want that the task marked as important to be displayed in important task page

    Given user opens task manager login page
    When user login to the application
    And user is able to add a task with with title,description and date
      | taskTitle | description                  | important |
      | AutoTest1 | Sample for automation test 1 | yes       |
      | AutoTest2 | Sample for automation test 2 | yes       |
      | AutoTest3 | Sample for automation test 2 | no        |
    Then user is able to see all the important task in important task page
      | taskTitle |
      | AutoTest1 |
      | AutoTest2 |