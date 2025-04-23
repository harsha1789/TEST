def run_performance_check(report_dir):
    path = f"{report_dir}/performance.txt"
    with open(path, "w") as f:
        f.write("Performance scan simulated. Use AST parser for real analysis.")
    return f"Performance scan saved to {path}"
