"use strict";
var stompClient = null;

var remotePath = "http://127.0.0.1:9001/ws-demo";

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $('#subscription').prop('disabled', !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS(remotePath);
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/halo', function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/halo", {}, JSON.stringify({'username': $("#name").val()}));
}

function testSubscription() {
    stompClient.subscribe('/app/subscribe', function (resp) {
        if (resp.body.indexOf('OK') != -1) {
            $('#send').prop('disabled', false);
            $('#send').parent().find('input[id="name"]').prop('disabled', false);
        } else {
            $('#send').prop('disabled', true);
            $('#send').parent().find('input[id="name"]').prop('disabled', true);
        }
    })
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    setConnected(false);
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () {
        connect();
    });
    $("#disconnect").click(function () {
        disconnect();
    });
    $("#send").click(function () {
        sendName();
    });
    $('#subscription').click(function () {
        testSubscription();
    });
});