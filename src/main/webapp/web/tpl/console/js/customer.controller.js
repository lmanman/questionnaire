
angular.module('app.console.customer.controllers', [])
    .controller('CustomerCtrl', ['$scope','$stateParams','$http',ctrlFn]);
;


function ctrlFn($scope,$stateParams,$http){
    $scope.id = 0;
    $scope.init = function(){
        $scope.id = $stateParams.id;
    }

    $scope.exhibitionList = [
        {"id":"1","name":"顾家沙发汶水店","address":"上海市宝山区汶水路1555号","dealerId":"1","brandId":"3","marketId":"1","cityId":"2","dealerName":"经销商A","brandName":"顾家","marketName":"红星美凯龙汶水店","cityName":"上海","createDate":"2016-11-22","remark":""},
        {"id":"2","name":"慕斯真北店","address":"上海市普陀区真北路1108号","dealerId":"2","brandId":"2","marketId":"2","cityId":"2","dealerName":"经销商B","brandName":"慕斯","marketName":"红星美凯龙真北店","cityName":"上海","createDate":"2016-12-16","remark":""},
    ];
    $scope.dealerList = [
        {"id":"1","name":"经销商A","linkman":"张三","telephone":"18711111111","email":"zs@visionet.com.cn"},
        {"id":"2","name":"经销商B","linkman":"李四","telephone":"18722222222","email":"ls@visionet.com.cn"},
    ];
    $scope.brandList = [
        {"id":"1","name":"圣象","mainId":2,"mainName":"建材"},
        {"id":"2","name":"慕斯","mainId":3,"mainName":"家居饰品"},
        {"id":"3","name":"顾家","mainId":1,"mainName":"家具"},
        {"id":"4","name":"科勒","mainId":2,"mainName":"建材"},
        {"id":"5","name":"海尔","mainId":4,"mainName":"电器"},
    ];
    $scope.marketList = [
        {"id":"1","name":"红星美凯龙汶水店","address":"上海市宝山区汶水路1555号","linkman":"xxx","cityName":"上海","createDate":"2016-11-22","remark":""},
        {"id":"2","name":"红星美凯龙真北店","address":"上海市普陀区真北路1108号","linkman":"xxx","cityName":"上海","createDate":"2016-11-22","remark":""},
        {"id":"3","name":"月星家居澳门店","address":"上海市普陀区澳门路168号","linkman":"xxx","cityName":"上海","createDate":"2016-10-13","remark":""},
    ];
    $scope.cityList = [
        {"id":1,"cityName":"北京"},
        {"id":2,"cityName":"上海"},
        {"id":3,"cityName":"广州"},
        {"id":4,"cityName":"深圳"},
    ];


    $scope.deleteEhb = function(index){
        $scope.exhibitionList.splice(index,1);
    }

    function submit(){

    }

}
