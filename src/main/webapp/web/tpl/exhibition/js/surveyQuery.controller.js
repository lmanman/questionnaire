
angular.module('app.exhibition.surveyQuery.controllers', [])
    .controller('SurveyQueryCtrl', ['$scope','$location','$log','$stateParams','$http',ctrlFn]);
;


function ctrlFn($scope,$location,$log,$stateParams,$http){

    $scope.dynamicType = function(a,b){
        var type;
        var value = a/b*100;
        if (value < 25) {
            type = 'danger';
        } else if (value < 50) {
            type = 'warning';
        } else if (value < 75) {
            type = 'info';
        } else {
            type = 'success';
        }
        return type;
    };


    $scope.formData = {
        pageInfo: {
            pageNumber: 1,
            pageSize: 10
        }
    };
    $scope.exhibitionSurveyList = [];
    $scope.query  = function(){
        var formData = {};
        formData.pageInfo = $scope.formData.pageInfo;
        formData.queryName = $scope.queryName;

        $http.post($scope.app.projectName + '/mobile/exhibition/survey/search',formData).success(function(result){

            $scope.exhibitionSurveyList = result;
        }).error(function(){
            alert('查询失败');
        });

    };


    $scope.newSurvey = function() {
        $location.path('/app/exhibition/surveyInput/0');
    };
    $scope.updateSurvey = function() {
        if(checkRadio()) {
            $location.path('/app/exhibition/surveyInput/' + $scope.checkId);
        }
    };

    $scope.checkId =0;
    $scope.updateShort = function() {
        console.info("checkId="+$scope.checkId);
        if(checkRadio()) {
            $location.path('/app/exhibition/surveyShortInput/' + $scope.checkId);
        }
    };
    $scope.newCopy = function() {
        if(checkRadio()) {
            $location.path('/app/exhibition/surveyInput/' + (-1 * $scope.checkId));
        }
    };

    function checkRadio(){
        if($scope.checkId==''||$scope.checkId==0){
            alert("请先选择问卷");
            return false;
        }
        return true;
    }

}
