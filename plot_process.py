import os
import sys
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt


def p2f(value: str) -> float:
    return float(value.strip('%'))

def process_plot_data(path: str)-> pd.DataFrame:
    data = pd.read_csv(os.path.join(path,"plot_data"),sep=",",skipinitialspace=True,converters={"valid_cov": p2f, "map_size": p2f})
    # normalize time
    data['# unix_time'] -= data["# unix_time"][0]
    # create new column: total inputs
    data['total_inputs'] = data['valid_inputs'] + data['invalid_inputs']
    data['total_inputs'] -= data["total_inputs"][0]

    # create a dataframe for time
    x_axis = "# unix_time"
    # eliminates duplicates in time
    time_based_data = data.copy().drop_duplicates(
        keep='first', subset=[x_axis])
    
    time_based_data = time_based_data[['# unix_time','total_inputs','valid_covered_probes','all_covered_probes']]

    return time_based_data

def get_final_df(path:str)->pd.DataFrame:
    path = os.path.join("plot_tests/jackson",path)
    dataframes = []
    for i in range(10):
        data = process_plot_data(os.path.join(path,"result"+str(i)))
        dataframes.append(data)
    
    final_coverage = [df.iloc[-1] for df in dataframes]
    final_df = pd.DataFrame(final_coverage)

    return final_df

def plot_box():

    variants = ["results_mut", "results_nomut", "results_ascii"]
    dataframes = []
    for variant in variants:
        final = get_final_df(variant)
        final_v = final.assign(Generator=variant)
        dataframes.append(final_v)

    data_combined = pd.concat(dataframes, ignore_index=True)

    sns.boxplot(x='Generator',y= "valid_covered_probes", data=data_combined)

    plt.savefig('plots/jackson/final_valid_coverage_box.pdf')

def plot_single():
    path = "plot_tests/jsonio/results_asciih"
    data = process_plot_data(path)
    sns.set_style("whitegrid")
    
    sns.lineplot(data=data,x="# unix_time", y='all_covered_probes')
    sns.lineplot(data=data,x="# unix_time", y='valid_covered_probes')
    plt.xlabel('Unix Time')
    plt.ylabel('Total Coverage')
    plt.title('Coverage Over Time')

    # Save the plot
    plt.savefig('plots/jsonio/asciih_coverage.pdf')
    plt.close()



def plot_median():
    # replace with own path to directory
    path = "plot_tests/jackson/results_nomut"
    dataframes = []
    for i in range(10):
        data = process_plot_data(os.path.join(path,"result"+str(i)))
        dataframes.append(data)

    merged_df = pd.concat(dataframes, ignore_index=True)
    max_end_times = [df['# unix_time'].max() for df in dataframes]
    min_end_time = min(max_end_times)

    filtered = merged_df[merged_df['# unix_time']<= min_end_time]
    result = filtered.groupby('# unix_time')['all_covered_probes'].median().reset_index()

    result_valid= filtered.groupby('# unix_time')['valid_covered_probes'].median().reset_index()

    result_valid.to_csv(os.path.join(path,"result_median_valid.csv"))

    sns.set_style("whitegrid")
    
    sns.lineplot(data=result,x="# unix_time", y='all_covered_probes')
    sns.lineplot(data=result_valid,x="# unix_time", y='valid_covered_probes')
    plt.xlabel('Unix Time')
    plt.ylabel('Total Coverage')
    plt.title('Coverage Over Time')

    # Save the plot
    #plt.savefig('plots/jackson/nomut_coverage_median.pdf')
    plt.close()

def main():
    #plot_single()
    plot_box()
    #plot_median()
    pass

if __name__ == "__main__":
    main()