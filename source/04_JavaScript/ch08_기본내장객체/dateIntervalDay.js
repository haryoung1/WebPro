// today.getIntervalDay(openday) : today부터 openday까지의 기간의 날짜(몇일이 지났는지를) return
// this는 today, that은 openday
Date.prototype.getIntervalDay = function(that) { // this ~ that 까지의 기간 날 return
    if (this>that) {
        var intervalMiliSec = this.getTime() - that.getTime();
    }else {
        var intervalMiliSec = that.getTime() - this.getTime();
    } // this와 that 사이의 밀리세컨을 구함
    var day = intervalMiliSec / (1000*60*60*24);
    return Math.trunc(day);
};
// var openday = new Date(2022, 10, 28, 9, 30, 0); // 개강시점
// var limitday = new Date(2023, 4, 12, 12, 20, 0);
// console.log(openday.getIntervalDay(limitday) + '일');
// console.log(new Date().getIntervalDay(limitday) + '일');