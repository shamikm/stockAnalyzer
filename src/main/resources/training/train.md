## How to train the model from the stock tweets
* Create the training set
java -cp "*" -mx8g edu.stanford.nlp.sentiment.BuildBinarizedDataset -input test.txt > train.txt
* Create the dev set
java -cp "*" -mx8g edu.stanford.nlp.sentiment.BuildBinarizedDataset -input test2.txt > dev.txt
* Create the model
java -cp "*" -mx8g edu.stanford.nlp.sentiment.SentimentTraining -numHid 25 -trainPath train.txt -devPath dev.txt -train -model model.ser.gz