import subprocess

def run_semgrep_scan():
    try:
        subprocess.run(["semgrep", "scan", "--config=auto", "--json", "--output", "repo_guardian_bot/reports/semgrep.json"], check=True)
        return "Static analysis saved to reports/semgrep.json"
    except Exception as e:
        return f"Semgrep scan failed: {str(e)}"
