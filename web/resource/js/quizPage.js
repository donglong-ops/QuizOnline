this.timeRemaining = 0;
this.timeDisplay = document.getElementById("timeDisplay");
this.questionPos = document.getElementById("qustionPos");
this.currentQuiz = 0;
this.currentDiv = null;
this.testing = 0;
this.numOfQuiz = 0;

var nextQuestion = function () {
    currentQuiz = currentQuiz % numOfQuiz;
    if (this.currentDiv !== null) {
        this.currentDiv.classList.add("hidden");
    }
    currentDiv = document.getElementById("q" + currentQuiz);
    currentDiv.classList.remove("hidden");
    questionPos.textContent = "Question: " + (currentQuiz + 1) + "/" + numOfQuiz;
    this.currentQuiz++;
}

var quizStart = function () {
    testing = 1;
    nextQuestion();
    setInterval(function () {
        timeRemaining--;
        updateTime();
        if (timeRemaining < 0) {
            testing = 0;
            document.getElementById("quizForm").submit();
        }
    }, 1000);
}

var updateTime = function () {
    var time = Math.floor(timeRemaining / 60);
    var sec = timeRemaining % 60;
    timeDisplay.textContent = time + ":" + sec;
}


var setNumOfQuiz = function (n) {
    numOfQuiz = n;
    this.timeRemaining = n*30; // mỗi câu hỏi có 30s, cứ dùng số câu hỏi * 30s
    this.timeDisplay = document.getElementById("timeDisplay");
    this.questionPos = document.getElementById("qustionPos");
    this.currentQuiz = 0;
    this.currentDiv = null;
    this.testing = 0;
}