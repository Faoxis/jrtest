var app = angular.module('taskApp', []);
app.controller('taskController', function($scope, $http) {
    $scope.mylist = ['one', 'two', 'three', 'four', 'five', 'six', 'seven'];

    $scope.pageTasks = [];
    $scope.pageCounter = 1;
    $scope.tasksPerPage = 10;
    $scope.selected = 'all';

    $scope.tasks = [];
    var updateAllTasks = function() {
        $http({
            method: 'GET',
            url: 'http://localhost:8085/tasks'
        }).then(function successCallback(response) {
            $scope.tasks = response.data;
            if ($scope.selected != 'all') {
                if ($scope.selected == 'done') {
                    $scope.tasks = $scope.tasks.filter(function (task) {
                        return !task.done;
                    });
                } else {
                    $scope.tasks = $scope.tasks.filter(function (task) {
                        return task.done;
                    });
                }
            }
            updatePageTasks();
        }, function errorCallback(response) {

        });
    };
    updateAllTasks();


    var updatePageTasks = function () {
        var startIndex = ($scope.pageCounter - 1) * $scope.tasksPerPage;
        $scope.pageTasks = $scope.tasks.slice(startIndex, startIndex + $scope.tasksPerPage);
    };


    $scope.next = function() {
        if ($scope.pageCounter * $scope.tasksPerPage < $scope.tasks.length) {
            $scope.pageCounter++;
            updatePageTasks();
        }
    };

    $scope.previous = function () {
        if ($scope.pageCounter > 1) {
            $scope.pageCounter--;
            updatePageTasks();
        }
    };

    $scope.resetTaskDone = function(task) {
        task.done = task.done ? false : true;
        $http.put('http://localhost:8085/tasks/' + task.id, task)
            .then(function () {
                updateAllTasks();
            })
    };

    $scope.addTask = function (taskName) {
        $http.post('http://localhost:8085/tasks', {name: taskName, done: false})
            .then(function () {
                updateAllTasks();
            })
    };

    $scope.deleteTask = function (task) {
        $http.delete('http://localhost:8085/tasks/' + task.id)
            .then(function () {
                updateAllTasks();
            })
    };


    $scope.selectAllTasks = function() {
        $scope.selected = 'all';
        updateAllTasks();
    };

    $scope.selectDoneTasks = function() {
        $scope.selected = 'done';
        updateAllTasks();
    };

    $scope.selectNotDoneTasks = function() {
        $scope.selected = 'not done';
        updateAllTasks();
    };

});
