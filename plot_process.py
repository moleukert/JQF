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
    # index for time
    #time_based_data = time_based_data.set_index(x_axis).reindex(
    #    range(1, time_based_data[x_axis].max(), 1)).interpolate().reset_index()
    time_based_data = time_based_data[['# unix_time','total_inputs','all_covered_probes']]

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
        final_v = final.assign(variant=variant)
        dataframes.append(final_v)

    data_combined = pd.concat(dataframes, ignore_index=True)
    
    sns.boxplot(x='variant',y= "all_covered_probes", data=data_combined)
     
    plt.savefig('plots/jackson/final_coverage_box.pdf')






def plot_median():
    # replace with own path to directory
    path = "plot_tests/mjson/results_nomut"
    dataframes = []
    for i in range(10):
        data = process_plot_data(os.path.join(path,"result"+str(i)))
        dataframes.append(data)

    merged_df = pd.concat(dataframes, ignore_index=True)
    max_end_times = [df['# unix_time'].max() for df in dataframes]
    min_end_time = min(max_end_times)

    filtered = merged_df[merged_df['# unix_time']<= min_end_time]
    result = filtered.groupby('# unix_time')['all_covered_probes'].median().reset_index()
    
    #result.to_csv('output.csv')
    sns.set_style("whitegrid")
    
    sns.lineplot(data=result,x="# unix_time", y='all_covered_probes')

    plt.xlabel('Unix Time')
    plt.ylabel('Total Coverage')
    plt.title('Coverage Over Time')

    # Save the plot
    plt.savefig('plots/mjsonx/nomut_coverage_median.pdf')
    plt.close()

def main():
    #plot_median()
    plot_box()
    pass

if __name__ == "__main__":
    main()