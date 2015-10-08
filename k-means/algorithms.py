import numpy as np
import random
from audioop import avg
from copy import deepcopy
from numpy.lib.function_base import average

class KMeans:
    
    """
    K-means Clustering; Returns K-centroids
    
    """
    
    def __init__(self,data,k,iterations):
        
        """ parameters are:
        
        k = number of clusters
        data = set of all data instances
        iterations = max number of iterations 
        
        """
        self.centroids = []
        
        self.prev_centroids = []
        
        #initializes previous centroids randomly
        self.initialize_centroids(data, k)
        
        #initializes current centroids
        self.initialize_optimal_(data, k)
        
        while len(self.centroids) > len(set(self.centroids)):
            self.initialize_optimal_(data, k)
        #print self.centroids
        
        
    def cluster(self,data,k,iterations):
        
        
        i = 0
        
        clusters =  [[] for i in range(k)]
        
        while not (self.do_i_stop(i, iterations)):
            
            # assign data points to cluster
            clusters = self.assign_data_to_cluster(k,data)
            
            #recalculate centroids
            self.recalculate_centroids(clusters, k)
            
        self.print_results(clusters)
        
    def initialize_centroids(self,data,k):    
        """ There are three ways to initialize centroids 
            1. To select the centroids randomly from the data points
            2. To select the centroids sequentially from the data points
            3. To select the first centroid randomly from the data point, then select the farthest point from first centroid,
               then the next farthest point from previous centroid, and so on till k centroids.
               
        """
        
        #Randomly selecting centroids from data points
        
        for i in range(k):
            self.prev_centroids.append(random.choice(data))
            
        print self.prev_centroids 
    
    
    
    def initialize_optimal_(self,data,k):
        #select the first centroid randomly and then rest centroids farthest apart from each other
        
        self.centroids.append(np.mean(data))

        i = 0
        
        for j in range(k-1):
            
            index = self.maximum_value_index(data,i)
            self.centroids.append(data[index])
            i = i+1
        
            
        print self.centroids
            
    
    
    def assign_data_to_cluster(self,k,data):
        
        """ First we need to find the distance of each data point from the centroids
            The data point will be assigned to corresponding centroid cluster for which the distance was minimum
        """
        
        #calculate distance of data points from centroids
        
        distance = []
        
        distance = list()
        
        clusters = [[] for i in range(k)]
        
        for d in data:
            
             temp = [d for i in range(k)]
            
             distance = np.abs(np.subtract(temp,self.centroids))
             
             index = self.minimum_value_index(distance)
             
             clusters[index].append(d)             
             
             
        return clusters    
        
        
    def recalculate_centroids(self, clusters, k):
        
        for i in range(k):
            
            self.prev_centroids[i] = self.centroids[i]
            
            self.centroids[i] = np.mean(clusters[i])
        
        
    def print_results(self, clusters):
        print clusters
        print self.centroids
        
    def do_i_stop(self,i,iterations):
        
        
        diff = np.abs(np.subtract(self.prev_centroids,self.centroids))
        
        if (diff.all()==0.0):
            print "yes"
            return True
        #elif i==iterations:
            #return True
        else:
            return False
        
    def minimum_value_index(self,distance):
        
        min = distance[0]
        index = 0
        
        for i in range(len(distance)):
            if distance[i]<min:
                min = distance[i]
                index = i
        
        return index
    
    def maximum_value_index(self,data,ind):
        
        distance = np.abs(np.subtract(data,self.centroids[ind]))
        
        max = distance[0]
        
        index = 0
                
        for i in range(len(distance)):
            if distance[i]>max:
                max = distance[i]
                index = i
        
        return index