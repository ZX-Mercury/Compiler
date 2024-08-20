.PHONY: build
build:
	find -name '*.java' | xargs javac -d bin -cp /ulib/antlr-4.13.2-complete.jar

.PHONY: run
run:
	cd bin && java -cp /ulib/antlr-4.13.2-complete.jar:. Main