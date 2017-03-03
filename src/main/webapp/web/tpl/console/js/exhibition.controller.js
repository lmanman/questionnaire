
angular.module('app.console.exhibition.controllers', [])
    .controller('ExhibitionCtrl', ['$scope','$stateParams','$http','$state','$log',ctrlFn]);
;


function ctrlFn($scope,$stateParams,$http,$state,$log){
    $scope.id = 0;
    $scope.storeInfo = {};
    $scope.dictionaryMap = {};


    $scope.detail = function(){
        $scope.id = $stateParams.id;
        $log.info("$scope.id="+$scope.id);

        var types = ['d_city','s_dealer','s_market'];
        $http.post($scope.app.projectName + '/mobile/dictionary/collect/map',types).success(function(map){
            $scope.dictionaryMap = map;
            $log.info("s_market.length="+$scope.dictionaryMap.s_market.length);

            if($scope.id!=0 && $scope.id!=''){
                $http.get($scope.app.projectName + '/mobile/exhibition/store/'+$scope.id).success(function(detail){
                    $scope.storeInfo = detail;
                    $log.info("name="+$scope.storeInfo.name);
                }).error(function(){
                    alert('请求失败');
                });
            }

        }).error(function(){
            alert('权限数据更新失败！');
        });

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


    $scope.saveStore = function(){
        var formData = {};
        if($scope.id!=0) {
            formData.id = $scope.storeInfo.id;
        }
        formData.name = $scope.storeInfo.name;
        formData.marketName = $scope.storeInfo.marketName;
        formData.address = $scope.storeInfo.address;
        if($scope.storeInfo.dealer!=undefined) {
            formData.dealerId = $scope.storeInfo.dealer.id;
        }
        if($scope.storeInfo.city!=undefined) {
            formData.cityId = $scope.storeInfo.city.id;
        }
        if($scope.storeInfo.market!=undefined) {
            formData.marketId = $scope.storeInfo.market.id;
        }

        $http.post($scope.app.projectName + '/mobile/exhibition/store/save', formData).success(function (result) {
            if (result.code == '10000') {
                alert("保存成功");
                $state.go('app.console.exhibitionList');
            } else {
                alert(result.msg);
                $state.go('app.console.exhibitionList');
            }
        }).error(function () {
            alert('提交失败');
        });
    };


    $scope.deleteEhb = function(id){
        if(confirm("是否确认删除！")) {
            $http.get($scope.app.projectName+'/mobile/exhibition/store/delete/'+id).success(function (result) {
                if(result.code=='10000'){
                    alert("删除成功");
                    $scope.query();
                }else{
                    alert(result.msg);
                }
            }).error(function () {
                alert('删除失败');
            });

        }
    };

    $scope.ui_detail = function(sid){
        $state.go("app.console.exhibitionDetail", {id:sid});
        //$state.go("app.console.exhibitionDetail", {id:sid}, { reload: true });
    }

}
