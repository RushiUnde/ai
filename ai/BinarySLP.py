#Binary Single Class Perceptron
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import copy


#signum fuction
def sgn(net):
    if net>=0:
        return 1
    else:
        return -1


if __name__ == "__main__":

    #Read data
    train = pd.read_csv("C:/Users/Lenovo/allfiles/ai/Big_Dataset.csv")

    #Store Desired values in numpy array
    desired = train["desired"].to_numpy()
    train = train.drop("desired",axis=1).to_numpy()
    
    #Array to store Errors
    Error_Array = np.zeros((2,20))

    #Traning Epochs
    maxepochs = 21

    ##initialization of weight vector using random values
    # I_Weight = np.random.uniform(-0.5,0.5,4)
    I_Weight = np.array([1,-1.5,1.5,0.5])
    
    for i in range(1,3):

        #initialization of weight vector
        Weight = copy.deepcopy(I_Weight)
        print(Weight)

        #initialize Learning rate
        LR = i
        epochs = 1
        Error = 1000

        while(Error != 0 and epochs < maxepochs):

            # initialize Error to zero
            Error = 0
            print(f"Epoch : {epochs}")
            for k in range(len(train)):

                net = 0
                #calculate net
                net = np.dot(train[k],Weight) 
                
                output = sgn(net)

                #update Weight Vector
                for i in range(4):
                    Weight[i] = Weight[i] + (0.5) * LR * (desired[k] - output) * train[k][i]
                    
                
                #update error for Training set k
                Error = Error + (1/2) * pow(desired[k] - output,2)

                print(f"Training Set {train[k]} output = {output} desired = {desired[k]}")

            print(f"Weight vector {Weight}")

            print(f"Error {Error}")
            Error_Array[LR - 1][epochs - 1] = Error
            print()
            epochs = epochs + 1

    epochs_list = np.arange(1,21)
    
    plt.plot(epochs_list,Error_Array[0],label = "C = 1",marker = 'o')    
    plt.plot(epochs_list,Error_Array[1],label = "C = 2",marker = 'o')
    plt.legend() 
    plt.xticks(epochs_list)
    plt.xlabel("Epochs")
    plt.ylabel("Errors")
    plt.show() 