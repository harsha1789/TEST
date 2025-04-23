def run_ssl_scan(report_dir):
    path = f"{report_dir}/ssl_scan.txt"
    with open(path, "w") as f:
        f.write("SSL scan simulated. Use SSLyze or cert validator.")
    return f"SSL scan saved to {path}"
