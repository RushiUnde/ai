#R-class Single Layer Perceptron
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import copy


#signum function
def sgn(net):
    if net>=0:
        return 1
    else:
        return -1


if __name__ == "__main__":

    #Read data
    train = pd.read_csv("C:/Users/Lenovo/allfiles/ai/Dataset.csv")

    #Store Desired values in numpy array
    Class = train["Class"].to_numpy()
    train = train.drop("Class",axis=1).to_numpy()
    
    #Traning Epochs
    maxepochs = 21

    #Array to store Errors
    Error_Array = np.zeros((2,maxepochs-1))

    Output = np.array([
                        [-1, -1, 1], 
                        [1, -1, -1],
                        [-1, 1, -1]
                                    ])
    
    #initialization of weight vector using small random values
    I_Weight = np.array([
                        [-0.1, 0.15, 0.2],
                        [-0.2, 0.11, 0.17],
                        [0.17, 0.16, 0.11]
                                        ])
    
    for c in range(1,3):

        #initialization of weight vector
        Weight = copy.deepcopy(I_Weight)
        print(Weight)

        # Initialize learning rate
        LR = c
        epochs = 1
        Error = 1000
        print(LR)
        while(Error != 0 and epochs < maxepochs):

            # initialize Error to zero
            Error = 0
            print(f"Epoch : {epochs}")
            for k in range(len(train)):

                net = np.zeros(3)

                #calculate net
                for i in range(3):
                    neti = np.dot(train[k],Weight[i]) 
                    net[i] = sgn(neti)
                
                #update Weight Vector
                for i in range(3):
                    for j in range(3):
                        Weight[i][j] = Weight[i][j] + (0.5 * LR * (Output[Class[k]][i] - net[i]) * train[k][j])
                    
                    #update error for Training set k
                    Error = Error + 0.5 * pow((Output[Class[k]][i] - net[i]),2)    
                
                print(f"Training Set {train[k]} output = {Output[Class[k]]} Actual = {net}")
            print(f"Weight vector {Weight}")
            print(f"Error {Error}")
            Error_Array[c - 1][epochs - 1] = Error
            print()
            epochs = epochs + 1

    epochs_list = np.arange(1,maxepochs)
    
    #plot line graph
    plt.figure(figsize=(12, 6))
    plt.plot(epochs_list,Error_Array[0],label = "C = 1",marker = 'o')    
    plt.plot(epochs_list,Error_Array[1],label = "C = 2",marker = 'o')
    plt.legend() 
    plt.xticks(epochs_list)
    plt.xlabel("Epochs")
    plt.ylabel("Errors")
    plt.show() 
