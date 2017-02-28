
angular.module('app.console.exhibition.controllers', [])
    .controller('ExhibitionCtrl', ['$scope','$stateParams','$http','$state','$log',ctrlFn]);
;


function ctrlFn($scope,$stateParams,$http,$state,$log){
    $scope.id = 0;
    $scope.init = function(){
        $scope.id = $stateParams.id;
    };


    $scope.formData = {
        pageInfo: {
            pageNumber: 1,
            pageSize: 10
        }
    };
    $scope.exhibitionList = [];
    $scope.query  = function(){
        var formData = {};
        formData.pageInfo = $scope.formData.pageInfo;
        formData.queryName = $scope.queryName;

        $http.post($scope.app.projectName + '/mobile/exhibition/store/search',formData).success(function(result){
            $scope.exhibitionList = result;
        }).error(function(){
            alert('查询失败');
        });
    };



    $scope.deleteEhb = function(index){
        $scope.exhibitionList.splice(index,1);
    };


}
