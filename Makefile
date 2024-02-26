.PHONY: build

build:
	gradle clean build

install:
	gradle clean installDist

lint:
	gradle checkstyleMain

test:
	gradle test

report:
	gradle test jacocoTestReport
