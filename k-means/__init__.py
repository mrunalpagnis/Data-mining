import algorithms as alg

if __name__ == '__main__':
    
    data = [-1,0,2,3,4,10,9,8,7,6,5,-3,-5,-2]#,11,12,13,14,15,16,17,18,19,20]
    
    k = 3
    
    iterations = 100000
    
    cluster = alg.KMeans(data,k,iterations)
    
    cluster.cluster(data,k,iterations)