import subprocess
import concurrent.futures

def run_command(command:list,args:list):
    # complete command with arguments for test cases
    for arg in args:
        if arg == 'r':
            command.insert(1,'-r')
        else:
            command.append(arg)
    try:
        process = subprocess.run(command, check=True, text=True, capture_output=True, timeout=900)
        print("Subprocess completed successfully.")
        print("Output:", process.stdout)
    # this only hides the errors caused by the forced timeout in guidance
    except subprocess.TimeoutExpired:
        print("Subprocess timed out after 900 seconds.")
    except subprocess.CalledProcessError as e:
        print("Subprocess failed with return code:", e.returncode)
        print("Error:", e.stderr)



def bash_test():
    # command without test method (for spezialization)
    command =[
    'bin/jqf-zest',
    '-f',
    '-c',
    subprocess.check_output(['scripts/examples_classpath.sh'], text=True).strip(),
    'edu.berkeley.cs.jqf.examples.gson.gson_test'
    ]

    test_methods = [['fuzzJSONParser_mut','result-full'],
                    ['fuzzJSONParser_nomut','result-nomut'],
                    ['fuzzJSONParser_ascii','result-ascii-full'],
                    ['fuzzJSONdeser_mut','result-mut-deser'],
                    ['fuzzJSONmin_ascii','result-ascii-min'],
                    ['fuzzJSONmin_mut','result-mut-min'],
                    ['fuzzJSONParser_mut','result-full-random','r']
                    ]
    with concurrent.futures.ProcessPoolExecutor(max_workers=len(test_methods)) as executor:
        
        futures = {executor.submit(run_command,command,arg): arg for arg in test_methods}

        concurrent.futures.wait(futures)


def main():
    bash_test()

if __name__ == "__main__":
    main()