/*!
 * Start Bootstrap - SB Admin 2 v3.3.7+1 (http://startbootstrap.com/template-overviews/sb-admin-2)
 * Copyright 2013-2016 Start Bootstrap
 * Licensed under MIT (https://github.com/BlackrockDigital/startbootstrap/blob/gh-pages/LICENSE)
 */
$(function() {
    $('#side-menu').metisMenu();
});

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function() {
    $(window).bind("load resize", function() {
        var topOffset = 50;
        var width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }

        var height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
    });

    var url = window.location;
    // var element = $('ul.nav a').filter(function() {
    //     return this.href == url;
    // }).addClass('active').parent().parent().addClass('in').parent();
    var element = $('ul.nav a').filter(function() {
        return this.href == url;
    }).addClass('active').parent();

    while (true) {
        if (element.is('li')) {
            element = element.parent().addClass('in').parent();
        } else {
            break;
        }
    }
    ko.applyBindings(new AppViewModel("AAPL"));
});

var donut;

function AppViewModel(symbol) {	
	var self = this;
	this.symbol = ko.observable(symbol);
    this.companyName = ko.observable();
    this.totalTweets = ko.observable();
    this.totalRecos = ko.observable();
    this.tweets = ko.observableArray([]);
    this.recos = ko.observableArray([]);
    
    this.currentPrice = ko.observable();
    this.openingPrice = ko.observable();
    this.priceChange = ko.observable();
    this.percentChange = ko.observable();
    this.peRatio = ko.observable();
    this.pegRatio = ko.observable();
    this.eps = ko.observable();
    this.ebitda = ko.observable();
    this.volume = ko.observable();
    this.marketCap = ko.observable();
    
    this.daysChange = ko.computed(function() {
        return this.priceChange() + " ("+this.percentChange() + ")";    
    }, this);    
    
    this.signal = ko.observable();
    this.decisionClass = ko.computed(function() {
        return this.signal() == "SELL"?"fa-thumbs-down":"fa-thumbs-up";
    }, this);
    this.decisionColor = ko.computed(function() {
        return this.signal() == "SELL"?"panel-red":"panel-green";
    }, this);
    
    self.updatePageData = function() {
	    $.getJSON("/rest/symbol/"+self.symbol(), function(allData) {
	        self.companyName(allData.symbol + " - " + allData.title);
	        self.totalTweets(allData.stockDetailList.length);
	        self.tweets(allData.stockDetailList);
	        self.recos(allData.financeReco);
	        self.totalRecos(allData.financeReco.length);
	        self.signal(allData.signal);
	        
	        var data = new Array();
	        var bullishPercent = new Object();
	        bullishPercent.label = 'Buy';
	        bullishPercent.value = parseFloat(allData.bullishPercent);
	        data.push(bullishPercent);
	        var bearishPercent = new Object();
	        bearishPercent.label = 'Sell';
	        bearishPercent.value = parseFloat(allData.bearishPercent);
	        data.push(bearishPercent);
	        var unclearPercent = new Object();
	        unclearPercent.label = 'Unclear';
	        unclearPercent.value = 100 - (parseFloat(allData.bullishPercent) + parseFloat(allData.bearishPercent));
	        data.push(unclearPercent);
	        if(donut==null){
	            donut = Morris.Donut({
	                element: 'morris-donut-chart',
	                data: data,
	                resize: true
	            });	        	
	        }
	        else donut.setData(data);
	    }); 
	    $.getJSON("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20IN%20(%22"+self.symbol()+"%22)&format=json&env=http://datatables.org/alltables.env", function(allData) {
	    	self.currentPrice('$ '+allData.query.results.quote.Ask);
	    	self.openingPrice('$ '+allData.query.results.quote.Open);
	    	self.eps(allData.query.results.quote.EarningsShare);
	    	self.priceChange('$ '+allData.query.results.quote.Change);
	    	self.marketCap('$ '+allData.query.results.quote.MarketCapitalization);
	    	self.peRatio(allData.query.results.quote.PERatio);
	    	self.pegRatio(allData.query.results.quote.PEGRatio);
	    	self.ebitda(allData.query.results.quote.EBITDA);
	    	self.percentChange(allData.query.results.quote.PercentChange);
	    	self.volume(allData.query.results.quote.Volume);
	    });
	    
    }
    self.updatePageData();
};
