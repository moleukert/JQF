import subprocess
import concurrent.futures
import time


def timer(seconds :int):
    start_time = time.time()
    while True:
        elapsed_time = time.time() - start_time
        print(f"Elapsed Time: {elapsed_time:.2f} seconds", end="\r")
        if elapsed_time >= seconds:
            break
        time.sleep(0.1)  # Adjust the sleep duration as needed for accuracy and performance

    print("\nTimer finished!")


def run_command(command:list,args:list):
    # complete command with arguments for test cases
    for arg in args:
        if arg == 'r':
            command.insert(1,'-r')
        else:
            command.append(arg)
    try:
        process = subprocess.run(command, check=True, text=True, capture_output=True, timeout=600)
        print("Subprocess completed successfully.")
        print("Output:", process.stdout)
    # this only hides the errors caused by the forced timeout in guidance
    except subprocess.TimeoutExpired:
        print("Subprocess timed out after 600 seconds.")
    except subprocess.CalledProcessError as e:
        print("Subprocess failed with return code:", e.returncode)
        print("Error:", e.stderr)



def bash_test():
    # command without test method (for spezialization)
    test_all_1 = False
    test_1_5 = True
    method = 'fuzzJSONParser_nomut'
    target_direct = 'plot_tests/'
    command =[
    'bin/jqf-zest',
    '-f',
    '-c',
    subprocess.check_output(['scripts/examples_classpath.sh'], text=True).strip(),
    'edu.berkeley.cs.jqf.examples.jackson.jackson_test'
    ]
    if test_all_1:
        test_methods = [['fuzzJSONParser_mut','result-full'],
                        ['fuzzJSONParser_nomut','result-nomut'],
                        ['fuzzJSONParser_ascii','result-ascii-full'],
                        ['fuzzJSONdeser_mut','result-mut-deser'],
                        ['fuzzJSONmin_ascii','result-ascii-min'],
                        ['fuzzJSONmin_mut','result-mut-min'],
                        ['fuzzJSONParser_mut','result-full-random','r']
                        ]
    elif test_1_5:
        command.append(method)
        test_methods = []
        
        for i in range(5):
          test_methods.append([target_direct+ 'jackson/results_nomut/result'+str(i+5)])  
    
    
    with concurrent.futures.ProcessPoolExecutor(max_workers=len(test_methods)) as executor:
        
        futures = {executor.submit(run_command,command,arg): arg for arg in test_methods}
        timer(600)
        concurrent.futures.wait(futures)

def main():
    bash_test()
    pass

if __name__ == "__main__":
    main()